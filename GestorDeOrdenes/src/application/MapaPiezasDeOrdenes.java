package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class MapaPiezasDeOrdenes {
		
	private Hashtable<Integer, ListaPiezas> map;

	public MapaPiezasDeOrdenes(Connection connection, MapaStock stockMap) throws SQLException {
		map = new Hashtable<>();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM orderParts");
		ResultSet data = statement.executeQuery();

		int orderNumber, pieceCode, cant;

		while(data.next()) {
			orderNumber	= Integer.parseInt(data.getObject("orderNumber").toString());
			pieceCode 	= Integer.parseInt(data.getObject("codPieza").toString());
			cant 		= Integer.parseInt(data.getObject("cant").toString());

			if(!contains(orderNumber)) {
				ListaPiezas partsList = new ListaPiezas();
				put(orderNumber, partsList);
			}


			Pieza aux = stockMap.get(pieceCode);

			if(aux != null) {
				aux.setCant(cant);
				put(orderNumber, aux);
			}
		}
	}

	public boolean put(int key, ListaPiezas list) {
		map.put(key, list);
		return true;
	}

	public boolean contains(int key) {
		return map.containsKey(key);
	}

	public boolean put(int key, Pieza part) {
		if(get(key) == null)
			return false;
		get(key).add(part);
		return true;
	}

	public boolean put(Orden order) {
		map.put(order.getOrderNumber(), order.getPartsList());
		return true;
	}

	public boolean remove(int key) {
		map.remove(key);
		return true;
	}

	public ListaPiezas get(int key) {
		return map.get(key);
	}

	public int size() {
		return map.size();
	}
}
