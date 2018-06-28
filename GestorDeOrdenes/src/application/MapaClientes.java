package application;

import com.mysql.jdbc.StringUtils;
import exceptions.MaxOrdenesSobrepasadoException;
import exceptions.RutInvalidoException;
import exceptions.TelefonoInvalidoException;
import exceptions.TieneOrdenesException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

public class MapaClientes {
		
	private Hashtable<Integer, Cliente> map;

	public MapaClientes(Connection connection, SMap clientOrdersMap) throws SQLException {
		map = new Hashtable<>();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM clientes");
		ResultSet data = statement.executeQuery();

		int rut, phoneNumber;
		String name, eMail;
		boolean isBusiness;

		while(data.next()) {
			rut 		= Integer.parseInt(data.getObject("rut").toString());
			name 		= data.getObject("nombre").toString();
			phoneNumber = Integer.parseInt(data.getObject("telefono").toString());
			eMail 		= data.getObject("eMail").toString();

			isBusiness = data.getObject("esEmpresa").toString().equals("true");

			ListaOrdenes orders = (ListaOrdenes) clientOrdersMap.get(rut);
			if(orders == null) {
				orders = new ListaOrdenes();
			}

			put(rut, name, phoneNumber, eMail, isBusiness, orders);
		}
	}

    private void put(int rut, String name, int phoneNumber, String eMail, boolean isBusiness, ListaOrdenes orders) {
        Cliente client = new Cliente(rut, name, phoneNumber, eMail, isBusiness, orders);
        map.put(rut, client);
    }

	public void put(String rut, String name, String phoneNumber, String eMail, boolean isBusiness, Connection connection) throws TelefonoInvalidoException, SQLException, RutInvalidoException {
        Cliente client = new Cliente(rut, name, phoneNumber, eMail, isBusiness);
        client.toDB(connection);
        map.put(Integer.parseInt(rut), client);
    }

    public boolean contains(int key) {
        return map.containsKey(key);
    }

	public void remove(int key, Connection connection) throws TieneOrdenesException, SQLException {

		if(!map.get(key).getOrders().isEmpty())
			throw new TieneOrdenesException();
		map.get(key).deleteFromDB(connection);
		map.remove(key);
	}

    public Cliente get(int key) {
        return map.get(key);
    }

	public Cliente get(String rut) throws RutInvalidoException {
        if(rut.length() > 8 || rut.length() < 7)
            throw new RutInvalidoException();

        if(!StringUtils.isStrictlyNumeric(rut))
            throw new RutInvalidoException();

        return map.get(Integer.parseInt(rut));
	}

	public void addOrder(Orden order) throws MaxOrdenesSobrepasadoException {
		get(order.getClientRut()).addOrder(order);
	}

	public void removeOrder(Orden order) {
		get(order.getClientRut()).removeOrder(order);
	}

	public ListaClientes toListaClientes() {
		Enumeration<Cliente> e = map.elements();
		ListaClientes list = new ListaClientes();

		while(e.hasMoreElements())
			list.add(e.nextElement());

		return list;
	}

	public int size() {
		return map.size();
	}
}

