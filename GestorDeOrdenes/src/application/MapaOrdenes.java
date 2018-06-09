package application;

import exceptions.SinStockException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

public class MapaOrdenes {
		
	private Hashtable<Integer, Orden> map;
	private int lastOrderNumber;

	public MapaOrdenes(Connection connection, MapaPiezasDeOrdenes orderPartsMap) throws SQLException {
		map = new Hashtable<>();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM ordenes");
		ResultSet data = statement.executeQuery();

		lastOrderNumber = -1;
		int orderNumber, clientRut, techNumber, price, complex;
		String description, dateIn, dateOut;
		boolean checked, done;

		while(data.next()) {
			orderNumber = Integer.parseInt(data.getObject("orderNumber").toString());
			description = data.getObject("descripcion").toString();
			dateIn 		= data.getObject("dateIn").toString();
			dateOut 	= data.getObject("dateOut").toString();
			clientRut 	= Integer.parseInt(data.getObject("clientRut").toString());
			techNumber 	= Integer.parseInt(data.getObject("techNumber").toString());
			price 		= Integer.parseInt(data.getObject("precio").toString());
			complex 	= Integer.parseInt(data.getObject("complejidad").toString());

			checked = data.getObject("revisada").toString().equals("true");

			done = data.getObject("terminada").toString().equals("true");

			ListaPiezas partsList = orderPartsMap.get(orderNumber);
			if(partsList == null)
				partsList = new ListaPiezas();

			put(orderNumber, description, dateIn, dateOut, clientRut, techNumber, price, partsList, complex, checked, done);

			if(orderNumber > lastOrderNumber)
			    lastOrderNumber = orderNumber;
		}
	}

	private void put(int orderNumber, String description, String dateIn, String dateOut, int clientRut, int techNumber, int price, ListaPiezas partsList, int complex, boolean checked, boolean done) {
		Orden aux = new Orden(orderNumber, description, dateIn, dateOut, clientRut, techNumber, price, partsList, complex, checked, done);
		map.put(orderNumber, aux);
	}

	public int createOrder(String description, int rutClient, int techNumber){
		Orden order = new Orden(getNewOrderNumber(), description, rutClient, techNumber);
		map.put(lastOrderNumber, order);
		return lastOrderNumber;
	}

	public boolean contains(int key) {
		return map.containsKey(key);
	}

	public boolean containsPart(int orderNumber, int partCode) {
        return map.get(orderNumber).containsPart(partCode);
    }

    public void addPart(int orderNumber, Pieza part) throws SinStockException {
	    map.get(orderNumber).addPart(part);
    }

    public void removePart(int orderNumber, int partCode) {
	    map.get(orderNumber).removePart(partCode);
    }

	public void remove(int key) {
		map.remove(key);
	}

	public void remove(int key, Connection connection) throws SQLException {
		map.get(key).deleteFromDB(connection);
		map.remove(key);
	}

	public Orden get(int key) {
		return map.get(key);
	}

	public ListaOrdenes toListaOrdenes() {
		Enumeration<Orden> e = map.elements();
		ListaOrdenes list = new ListaOrdenes();

		while(e.hasMoreElements())
			list.add(e.nextElement());

		return list;
	}

	public int size() {
		return map.size();
	}

	public int getProfit() {
		int i = 0, sum = 0;
		ListaOrdenes aux = toListaOrdenes();

		while(i < aux.size()) {
			sum += aux.get(i).getProfit();
			i++;
		}

		return sum;
	}

	private int getNewOrderNumber() {
		lastOrderNumber++;
		return lastOrderNumber;
	}
}
