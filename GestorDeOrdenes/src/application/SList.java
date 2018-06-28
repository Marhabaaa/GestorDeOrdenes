package application;

import interfaces.Lista;

import java.util.ArrayList;

public class SList implements Lista {

	private ArrayList<Object> list;
	
	public SList() {
		list = new ArrayList<>();
	}
	
	public void add(Object o) {
		list.add(o);
	}
	
	public void remove(Object o) {
		list.remove(o);
	}
	
	public Object get(int index) {
		return list.get(index);
	}

	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
}

