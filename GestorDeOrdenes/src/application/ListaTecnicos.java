package application;

import interfaces.Lista;

import java.util.ArrayList;

public class ListaTecnicos implements Lista {

    private ArrayList<Tecnico> list;

    public ListaTecnicos(){
        list = new ArrayList<>();
    }

    public boolean add(Tecnico tecnico) {
        list.add(tecnico);
        return true;
    }

    public boolean remove(Tecnico tecnico) {
        list.remove(tecnico);
        return true;
    }

    public Tecnico get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
