package deque;

public class ArrayDeque<T> {
    private T[] first;
    private int fsize;
    private int lsize;
    private T[] last;

    public ArrayDeque(){
        first = (T[]) new Object[8];
        last = (T[]) new Object[8];
        fsize = 0;
        lsize = 0;
    }

    public void addFirst(T item){
        if(fsize + 1 > first.length){
            first = resize(fsize * 2,first);
        }
        if(fsize < first.length / 4){
            first = resize(first.length / 2,first);
        }
        if(fsize < 0){
            last[-fsize-1] = item;
        }else {
            first[fsize] = item;
        }
        fsize += 1;
    }

    public void addLast(T item){
        if(lsize + 1 > last.length){
            last = resize(lsize * 2,last);
        }
        if(lsize < last.length / 4){
            last = resize(last.length / 2,last);
        }
        if(lsize < 0){
            first[-lsize-1] = item;
        }else{
            last[lsize] = item;
        }
        lsize += 1;
    }

    public boolean isEmpty(){
        if(fsize+lsize == 0){
            return true;
        }
        return false;
    }

    public int size(){
        return fsize+lsize;
    }

    public void printDeque(){
        for(int i = fsize-1;i >= 0;i--){
            System.out.print(first[i].toString()+" ");
        }
        for(int i = 0;i < lsize;i++){
            System.out.print(last[i].toString()+" ");
        }
        System.out.println();
    }

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

    public T get(int index){
        if(fsize+lsize == 0 || index+1 > fsize+lsize){
            return null;
        }
        if(fsize <= index+1){
            return first[fsize-index-1];
        }
        return last[index-fsize];
    }

    public T[] resize(int s,T[] array){
        T[] newArray = (T[]) new Object[s];
        for(int i = 0;i < (fsize < s ? fsize:s);i++){
            newArray[i] = array[i];
        }
        return newArray;
    }

}
