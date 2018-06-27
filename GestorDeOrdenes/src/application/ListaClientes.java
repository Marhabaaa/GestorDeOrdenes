package application;

import interfaces.Lista;

import java.util.ArrayList;

public class ListaClientes implements Lista {

    private ArrayList<Cliente> list;

    public ListaClientes(){
        list = new ArrayList<>();
    }

    public boolean add(Cliente cliente) {
        list.add(cliente);
        return true;
    }

    public boolean remove(Cliente cliente) {
        list.remove(cliente);
        return true;
    }

    public Cliente get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}

