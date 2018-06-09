package application;

import exceptions.RutInvalidoException;
import exceptions.SinTecnicosException;
import exceptions.TelefonoInvalidoException;
import exceptions.TieneOrdenesException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

public class MapaTecnicos {
		
	private Hashtable<Integer, Tecnico> map;
	private int lastTechNumber;

	public MapaTecnicos(Connection connection, SMap techOrdersMap) throws SQLException {
		map = new Hashtable<>();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM tecnicos");
		ResultSet data = statement.executeQuery();

		lastTechNumber = -1;
		int rut, phoneNumber, techNumber, dwh;
		String name, eMail;

		while(data.next()) {
			rut 		= Integer.parseInt(data.getObject("rut").toString());
			name 		= data.getObject("nombre").toString();
			phoneNumber = Integer.parseInt(data.getObject("telefono").toString());
			eMail 		= data.getObject("eMail").toString();
			techNumber 	= Integer.parseInt(data.getObject("tecNumber").toString());
			dwh 		= Integer.parseInt(data.getObject("dwh").toString());

			ListaOrdenes orders = (ListaOrdenes) techOrdersMap.get(techNumber);
			if(orders == null) {
				orders = new ListaOrdenes();
			}

			put(rut, name, phoneNumber, eMail, techNumber, dwh, orders);

			if(techNumber > lastTechNumber)
				lastTechNumber = techNumber;
		}
	}

	public boolean contains(int key) {
		return map.containsKey(key);
	}

	public void put(int rut, String name, int phoneNumber, String eMail, int techNumber, int dwh, ListaOrdenes orders) {
	    Tecnico tech = new Tecnico(rut, name, phoneNumber, eMail, techNumber, dwh, orders);
		map.put(tech.getTechNumber(), tech);
	}

    public void put(String rut, String name, String phoneNumber, String eMail, int dwh, Connection connection) throws SQLException, TelefonoInvalidoException, RutInvalidoException {
        Tecnico tech = new Tecnico(rut, name, phoneNumber, eMail, getNewTechNumber(), dwh);
        tech.toDB(connection);
        map.put(tech.getTechNumber(), tech);
    }

	public void remove(int key, Connection connection) throws TieneOrdenesException, SQLException {
		if(!map.get(key).getOrders().isEmpty())
			throw new TieneOrdenesException();
		map.get(key).deleteFromDB(connection);
		map.remove(key);
	}

	public Tecnico get(int key) {
		return map.get(key);
	}

	public void addOrder(Orden order) {
		get(order.getTechNumber()).addOrder(order);
	}

	public void removeOrder(Orden order) {
		get(order.getTechNumber()).removeOrder(order);
	}


	public int size() {
		return map.size();
	}

	public int leastWorkload() throws SinTecnicosException {
		ListaTecnicos techsList = toListaTecnicos();
		if(techsList.isEmpty())
			throw new SinTecnicosException();

		int i = 1, least, techNumber;
		least = techsList.get(0).getWorkload();
		techNumber = techsList.get(0).getTechNumber();

		while(i < techsList.size()) {
			if(techsList.get(i).getWorkload() < least) {
                least = techsList.get(i).getWorkload();
                techNumber = techsList.get(i).getTechNumber();
            }
			i++;
		}

		return techNumber;
	}

	private int getNewTechNumber() {
		lastTechNumber++;
		return lastTechNumber;
	}

	public ListaTecnicos toListaTecnicos() {
		Enumeration<Tecnico> e = map.elements();
		ListaTecnicos list = new ListaTecnicos();

		while(e.hasMoreElements())
			list.add(e.nextElement());

		return list;
	}
}
