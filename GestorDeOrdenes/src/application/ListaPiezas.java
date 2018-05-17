package application;

import java.util.ArrayList;

public class ListaPiezas implements Lista{

    private ArrayList<Pieza> list;

    public ListaPiezas(){
        list = new ArrayList<>();
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
                list.get(getIndex(part.getCode())).oneMore();
            else
                list.add(part);
    }

    public void remove(int code) {
        if(list.get(getIndex(code)).getCant() > 1)
            try {
                list.get(getIndex(code)).oneLess();
            }
            catch(SinStockException e) {
                System.out.println(e.getMessage());
            }
        else
            list.remove(getIndex(code));
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
