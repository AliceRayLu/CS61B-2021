package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>{
    private T[] first;
    private int fsize;
    private int lsize;
    private T[] last;

    public ArrayDeque() {
        first = (T[]) new Object[8];
        last = (T[]) new Object[8];
        fsize = 0;
        lsize = 0;
    }

    @Override
    public void addFirst(T item){
        if(fsize + 1 >= first.length){
            first = resize(fsize * 2,first,lsize);
        }
        if(fsize < 0){
            last[-fsize-1] = item;
        }else {
            first[fsize] = item;
        }
        fsize += 1;
    }

    @Override
    public void addLast(T item){
        if(lsize + 1 >= last.length){
            last = resize(lsize * 2,last,lsize);
        }

        if(lsize < 0){
            first[-lsize-1] = item;
        }else{
            last[lsize] = item;
        }
        lsize += 1;
    }

    @Override
    public int size(){
        return fsize+lsize;
    }

    @Override
    public void printDeque(){
        for(int i = fsize-1;i >= 0;i--){
            System.out.print(first[i].toString()+" ");
        }
        for(int i = 0;i < lsize;i++){
            System.out.print(last[i].toString()+" ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst(){
        if(fsize+lsize == 0){
            return null;
        }
        T res;
        if(fsize <= 0){
            res = last[-fsize];
            last[-fsize] = null;
        }else {
            res = first[fsize-1];
            first[fsize-1] = null;
        }
        fsize -= 1;
        return res;
    }

    @Override
    public T removeLast(){
        if(fsize+lsize == 0){
            return null;
        }
        T res;
        if(lsize <= 0){
            res = first[-lsize];
            first[-lsize] = null;
        }else {
            res = last[lsize-1];
            last[lsize-1] = null;
        }
        lsize -= 1;
        return res;
    }

    @Override
    public T get(int index){
        if(size() == 0 || index+1 > size()){
            return null;
        }
        if(fsize <= index+1){
            return last[index-fsize];
        }
        return first[fsize-index-1];

    }

    private T[] resize(int s,T[] array,int originSize){
        T[] newArray = (T[]) new Object[s];
        for(int i = 0;i < (originSize < s ? originSize:s);i++){
            newArray[i] = array[i];
        }
        return newArray;
    }

    public Iterator<T> iterator(){
        //TODO
        return null;
    }

    public boolean equals(Object o){
        int flag = 1;
        if(o instanceof Deque){
            if(((Deque<T>) o).size() == size()){
                for(int i = 0;i < size();i++){
                    if(((Deque<T>) o).get(i) != get(i)){
                        flag = 0;
                        break;
                    }
                }
                if(flag == 1){
                    return true;
                }
            }
        }
        return false;
    }

}
