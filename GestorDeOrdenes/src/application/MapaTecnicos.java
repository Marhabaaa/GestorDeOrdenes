package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

public class MapaTecnicos {
		
	private Hashtable<Integer, Tecnico> map;

	public MapaTecnicos(Connection connection, SMap techOrdersMap) throws SQLException {
		map = new Hashtable<>();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM tecnicos");
		ResultSet data = statement.executeQuery();

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

			Tecnico aux = new Tecnico(rut, name, phoneNumber, eMail, techNumber, dwh, orders);

			put(aux);

			//setTechNumber(techNumber);
		}
	}

	public boolean contains(int key) {
		return map.containsKey(key);
	}

	public boolean put(Tecnico tech) {
		map.put(tech.getRut(), tech);
		return true;
	}

	public boolean remove(int key) {
		map.remove(key);
		return true;
	}

	public Tecnico get(int key) {
		return map.get(key);
	}

	public boolean addOrder(Orden order, int techNumber) {
		return get(techNumber).addOrder(order);
	}

	public boolean removeOrder(Orden order) {
		return get(order.getTechNumber()).removeOrder(order);
	}

	public SList toSList() {
		Enumeration<Tecnico> e = map.elements();
		SList list = new SList();

		while(e.hasMoreElements())
			list.add(e.nextElement());

		return list;
	}

	public int size() {
		return map.size();
	}

	public int leastWorkload() throws SinTecnicosException {
		SList techsList = toSList();
		if(techsList.isEmpty())
			throw new SinTecnicosException();

		int i = 1, least;
		least = ((Tecnico) techsList.get(0)).getWorkload();

		while(i < techsList.size()) {
			if(((Tecnico) techsList.get(i)).getWorkload() < least)
				least = ((Tecnico) techsList.get(i)).getWorkload();
			i++;
		}

		return least;
	}
}
