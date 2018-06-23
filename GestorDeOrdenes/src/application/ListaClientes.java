package application;


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
