package application;

import java.util.ArrayList;

public class ListaPiezas {

    private ArrayList<Pieza> list;

    public ListaPiezas(){
        list = new ArrayList<>();
    }

    public boolean add(Pieza part) {
        list.add(part);
        return true;
    }

    public boolean remove(Pieza part) {
        list.remove(part);
        return true;
    }

    public Pieza get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
