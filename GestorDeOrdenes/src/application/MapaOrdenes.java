package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

public class MapaOrdenes {
		
	private Hashtable<Integer, Orden> map;

	public MapaOrdenes(Connection connection, MapaPiezasDeOrdenes orderPartsMap) throws SQLException {
		map = new Hashtable<>();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM ordenes");
		ResultSet data = statement.executeQuery();

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

			Orden aux = new Orden(orderNumber, description, dateIn, dateOut, clientRut, techNumber, price, partsList, complex, checked, done);

			put(aux);

			//setOrderNumber(orderNumber);
		}
	}

	public boolean contains(int key) {
		return map.containsKey(key);
	}

	public boolean put(Orden order) {
		map.put(order.getOrderNumber(), order);
		return true;
	}

	public boolean remove(int key) {
		map.remove(key);
		return true;
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

	public Orden[] toArray() {
		Enumeration<Orden> e = map.elements();
		Orden[] aux = new Orden[map.size()];
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

	public int getProfit() {
		int i = 0, sum = 0;
		ListaOrdenes aux = toListaOrdenes();

		while(i < aux.size()) {
			sum += aux.get(i).getProfit();
			i++;
		}

		return sum;
	}
}
