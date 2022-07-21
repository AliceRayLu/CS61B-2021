package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>{
    private class Node{
        public T value;
        public Node next;
        public Node before;

        public Node(T item,Node n,Node b){
            value = item;
            next = n;
            before = b;
        }
    }
    private Node first;
    private int size;

    public LinkedListDeque(){
        size = 0;
        first = null;
    }

    @Override
    public void addFirst(T item){
        size += 1;
        if(first == null){
            first = new Node(item,null,null);
            first.before = first;
            first.next = first;
        }else{
            Node newNode = new Node(item,first,first.before);
            first.before.next = newNode;
            first.before = newNode;
            first = newNode;
        }
    }

    @Override
    public void addLast(T item){
        size += 1;
        if(first == null){
            first = new Node(item,null,null);
            first.before = first;
            first.next = first;
        }else{
            Node newNode = new Node(item,first,first.before);
            first.before.next = newNode;
            first.before = newNode;
        }
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        if(first != null){
            System.out.print(first.value.toString());
            Node list = first;
            while(list.next != first){
                list = list.next;
                System.out.print(" "+first.value.toString());
            }
            System.out.println();
        }
    }

    @Override
    public T removeFirst(){
        if(first == null){
            return null;
        }
        T item = first.value;
        first.before.next = first.next;
        first.next.before = first.before;
        first = first.next;
        size -= 1;
        if(size == 0){
            first = null;
        }
        return item;
    }

    @Override
    public T removeLast(){
        if(first == null){
            return null;
        }
        size -= 1;
        T item = first.before.value;
        first.before.before.next = first;
        first.before = first.before.before;
        if(size == 0){
            first = null;
        }
        return item;
    }

    @Override
    public T get(int index){
        int t = 0;
        if(index >= size){
            return null;
        }
        Node list = first;
        while (t != index){
            list = list.next;
            t++;
        }
        return list.value;
    }

    public T getRecursive(int index){
        if(index >= size || index < 0){
            return null;
        }
        return helper(index).value;
    }

    private Node helper(int index){
        if(index == 0){
            return first;
        }
        return helper(index - 1).next;
    }

    public boolean equals(Object o){
        int flag = 1;
        if(o instanceof Deque){
            if(((Deque<T>) o).size() == this.size()){
                for(int i = 0;i < size();i++){
                    if(!((Deque<T>) o).get(i).equals(get(i))){
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

    private class LLDIterator implements Iterator<T>{
        private int pointer;

        public LLDIterator(){
            pointer = 0;
        }
        @Override
        public boolean hasNext() {
            return pointer < size;
        }

        @Override
        public T next() {
            T nextItem = get(pointer);
            pointer++;
            return nextItem;
        }
    }
    public Iterator<T> iterator(){
        return new LLDIterator();
    }
}
