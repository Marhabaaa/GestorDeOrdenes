package application;

import exceptions.SinStockException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

public class MapaStock {

	private Hashtable<Integer, Pieza> map;

	public MapaStock(Connection connection) throws SQLException {
	    PreparedStatement statement = connection.prepareStatement("SELECT * FROM inventario");
		ResultSet data = statement.executeQuery();

		int code, cant, price, complex;
		String description;
		map = new Hashtable<>();

		while(data.next()) {
			code = Integer.parseInt(data.getObject("codPieza").toString());
			description = data.getObject("descripcion").toString();
			cant = Integer.parseInt(data.getObject("cant").toString());
			price = Integer.parseInt(data.getObject("precioUnit").toString());
			complex = Integer.parseInt(data.getObject("complejidad").toString());

			put(code, description, cant, price, complex);
		}
	}

	private void put(int code, String description, int cant, int price, int complex) {
        Pieza aux = new Pieza(code, description, cant, price, complex);
        map.put(code, aux);
    }

    public void put(int code, String description, int cant, int price, int complex, Connection connection) throws SQLException {
        Pieza aux = new Pieza(code, description, cant, price, complex);
        aux.toDB(connection);
        map.put(code, aux);
    }

    public void updateStock(int code, int cant) throws SinStockException {
	    map.get(code).updateCant(cant);
    }

	public boolean contains(int key) {
		return map.containsKey(key);
	}

	public void remove(int key, Connection connection) throws SQLException {
		map.get(key).deleteFromDB(connection);
		map.remove(key);
	}

	public Pieza get(int key) {
		return map.get(key);
	}

	public ListaPiezas toListaPiezas() {
		Enumeration<Pieza> e = map.elements();
		ListaPiezas list = new ListaPiezas();

		while (e.hasMoreElements())
			list.copyPart(e.nextElement());

		return list;
	}

	public int size() {
		return map.size();
	}
}
