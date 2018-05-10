package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

public class MapaStock {
		
		private Hashtable<Integer, Pieza> map;
		
		public MapaStock(Connection connection) throws SQLException {
			map = new Hashtable<>();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM inventario");
			ResultSet data = statement.executeQuery();

			int code, cant, price, complex;
			String description;

			while(data.next()) {
				code = Integer.parseInt(data.getObject("codPieza").toString());
				description = data.getObject("descripcion").toString();
				cant = Integer.parseInt(data.getObject("cant").toString());
				price = Integer.parseInt(data.getObject("precioUnit").toString());
				complex = Integer.parseInt(data.getObject("complejidad").toString());

				Pieza aux = new Pieza(code, description, cant, price, complex);

				put(aux);
			}
		}
		
		public boolean contains(int key) {
			return map.containsKey(key);
		}
		
		public boolean put(Pieza part) {
			map.put(part.getCode(), part);
			return true;
		}
		
		public boolean remove(int key) {
			map.remove(key);
			return true;
		}
		
		public Pieza get(int key) {
			return map.get(key);
		}
		
		public SList toSList() {
			Enumeration<Pieza> e = map.elements();
			SList list = new SList();
			
			while(e.hasMoreElements())
				list.add(e.nextElement());
			
			return list;
		}
		
		public Pieza[] toArray() {
			Enumeration<Pieza> e = map.elements();
			Pieza[] aux = new Pieza[map.size()];
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
		
		public String[] getStockDescriptions() {
			int i = 0;
			SList list = toSList();
			String[] s = new String[list.size()];
			
			while(i < list.size()) {
				s[i] = ((Pieza) list.get(i)).getDescription() + " " + ((Pieza) list.get(i)).getCode();
				i++;
			}
			
			return s;
		}
}
