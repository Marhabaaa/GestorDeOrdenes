package application;

import interfaces.Lista;

import java.util.ArrayList;

public class ListaOrdenes implements Lista {

    private ArrayList<Orden> list;

    public ListaOrdenes(){
        list = new ArrayList<>();
    }

    public boolean add(Orden order) {
        list.add(order);
        return true;
    }

    public void remove(Orden order) {
        list.remove(order);
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

