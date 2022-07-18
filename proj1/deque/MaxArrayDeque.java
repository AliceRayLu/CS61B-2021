package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private T[] first;
    private int fsize;
    private int lsize;
    private T[] last;
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c){
        comparator = c;
        first = (T[]) new Object[8];
        last = (T[]) new Object[8];
        fsize = 0;
        lsize = 0;
    }

    public T max(){
        if(lsize+fsize == 0){
            return null;
        }
        //TODO
        return null;
    }

    public T max(Comparator<T> c){
        //TODO
        return null;
    }
}
