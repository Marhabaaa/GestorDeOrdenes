package application;

import exceptions.SinTecnicosException;
import exceptions.TieneOrdenesException;

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

			put(rut, name, phoneNumber, eMail, techNumber, dwh, orders);

			//setTechNumber(techNumber);
		}
	}

	public boolean contains(int key) {
		return map.containsKey(key);
	}

	public boolean put(int rut, String name, int phoneNumber, String eMail, int techNumber, int dwh, ListaOrdenes orders) {
	    Tecnico tech = new Tecnico(rut, name, phoneNumber, eMail, techNumber, dwh, orders);
		map.put(tech.getTechNumber(), tech);
		return true;
	}

    public boolean put(int rut, String name, int phoneNumber, String eMail, int techNumber, int dwh) {
        Tecnico tech = new Tecnico(rut, name, phoneNumber, eMail, techNumber, dwh);
        map.put(tech.getTechNumber(), tech);
        return true;
    }

	public void remove(int key) throws TieneOrdenesException {
		if(!map.get(key).getOrders().isEmpty())
			throw new TieneOrdenesException();
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

	public ListaTecnicos toListaTecnicos() {
		Enumeration<Tecnico> e = map.elements();
		ListaTecnicos list = new ListaTecnicos();

		while(e.hasMoreElements())
			list.add(e.nextElement());

		return list;
	}
}
