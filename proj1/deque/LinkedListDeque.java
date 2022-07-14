package deque;

public class LinkedListDeque<T> {
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

    public boolean isEmpty(){
        if(size == 0){
            return true;
        }
        return false;
    }

    public int size(){
        return size;
    }

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

    public Node helper(int index){
        if(index == 0){
            return first;
        }
        return helper(index - 1).next;
    }

    public boolean equals(Object o){
        int flag = 1;
        if(o instanceof LinkedListDeque){
            Node list = first;
            Node tryList = ((LinkedListDeque<T>) o).first;
            for(;list.next != first && tryList.next != ((LinkedListDeque<T>) o).first;list = list.next,tryList = tryList.next){
                if(list.value != tryList.value){
                    flag = 0;
                    break;
                }
            }
            if(flag == 1){
                return true;
            }
        }
        return false;
    }
}
