package application;

import java.util.Enumeration;
import java.util.Hashtable;

public class SMap {
	
	private Hashtable<Integer, Object> map;
	
	public SMap() {
		map = new Hashtable<>();
	}
	
	public boolean contains(int key) {
		return map.containsKey(key);
	}
	
	public boolean put(int key, Object o) {
		map.put(key, o);
		return true;
	}
	
	public boolean remove(int key) {
		map.remove(key);
		return true;
	}
	
	public Object get(int key) {
		return map.get(key);
	}
	
	public SList toSList() {
		Enumeration<Object> e = map.elements();
		SList list = new SList();
		
		while(e.hasMoreElements())
			list.add(e.nextElement());
		
		return list;
	}
	
	public Object[] toArray() {
		Enumeration<Object> e = map.elements();
		Object[] aux = new Object[map.size()];
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
