package application;

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

			Cliente aux = new Cliente(rut, name, phoneNumber, eMail, isBusiness, orders);

			put(aux);
		}
	}

	public boolean contains(int key) {
		return map.containsKey(key);
	}

	public boolean put(Cliente client) {
		map.put(client.getRut(), client);
		return true;
	}

	public boolean remove(int key) {
		map.remove(key);
		return true;
	}

	public Cliente get(int key) {
		return map.get(key);
	}

	public boolean addOrder(Orden order, int rut) {
		return get(rut).addOrder(order);
	}

	public boolean removeOrder(Orden order) {
		return get(order.getClientRut()).removeOrder(order);
	}

	public SList toSList() {
		Enumeration<Cliente> e = map.elements();
		SList list = new SList();

		while(e.hasMoreElements())
			list.add(e.nextElement());

		return list;
	}

	public Cliente[] toArray() {
		Enumeration<Cliente> e = map.elements();
		Cliente[] aux = new Cliente[map.size()];
		int i = 0;

		while(e.hasMoreElements()) {
			aux[i] = e.nextElement();
			i++;
		}

		return aux;
	}

	public int size() {
		return map.size();
	}
}
