package application;

import java.util.ArrayList;

public class ListaOrdenes {

    private ArrayList<Orden> list;

    public ListaOrdenes(){
        list = new ArrayList<>();
    }

    public boolean add(Orden order) {
        list.add(order);
        return true;
    }

    public boolean remove(Orden order) {
        list.remove(order);
        return true;
    }

    public Orden get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
