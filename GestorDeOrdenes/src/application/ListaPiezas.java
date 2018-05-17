package application;

import java.util.ArrayList;

public class ListaPiezas {

    private ArrayList<Pieza> list;

    public ListaPiezas(){
        list = new ArrayList<>();
    }

    public boolean remove(Pieza part) {
        list.remove(part);
        return true;
    }

    public Pieza get(int index) {
        return list.get(index);
    }

    public int getIndex(int code) {
        int i = 0;
        while(i < list.size()) {
            if(list.get(i).getCode() == code)
                break;
            i++;
        }

        return i;
    }

    public boolean contains(int partCode) {
        int i = 0;
        while(i < list.size()) {
            if(list.get(i).getCode() == partCode)
                return true;
            i++;
        }

        return false;
    }

    public void add(Pieza part) {
            if(contains(part.getCode()))
                list.get(getIndex(part.getCode())).updateCant(1);
            else
                list.add(part);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
