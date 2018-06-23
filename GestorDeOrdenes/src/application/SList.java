package application;

import java.util.ArrayList;
import java.util.List;

public class SList implements Lista {
	private ArrayList<Object> list;
	
	public SList() {
		list = new ArrayList<>();
	}
	
	public boolean add(Object o) {
		list.add(o);
		return true;
	}
	
	public boolean remove(Object o) {
		list.remove(o);
		return true;
	}
	
	public Object get(int index) {
		return list.get(index);
	}

	@Override
	public boolean add() {
		return false;
	}

	@Override
	public boolean remove() {
		return false;
	}

	@Override
	public Object get() {
		return null;
	}

	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
}
