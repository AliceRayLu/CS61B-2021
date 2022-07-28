package bstmap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable,V> implements Map61B<K,V>{
    private int size;
    private BSTNode root;
    private Set<K> set ;

    public BSTMap(){
        size = 0;
        root = null;
        set = new HashSet<>();
    }
    private class BSTNode{
        private K key;
        private V value;
        public BSTNode left,right;

        public BSTNode(K k,V v){
            key = k;
            value = v;
            left = null;
            right = null;
        }
        public K getKey(){
            return key;
        }
        public V getValue(){
            return value;
        }
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
        set.clear();
    }

    @Override
    public boolean containsKey(K key) {
        return set.contains(key);
    }

    @Override
    public V get(K key) {
        for(BSTNode p = root;;){
            if(p == null){
                break;
            }
            if(p.getKey().compareTo(key) == 0){
                return p.getValue();
            }else if(key.compareTo(p.getKey()) < 0){
                if(p.left != null){
                    p = p.left;
                }else{
                    break;
                }
            }else{
                if(p.right != null){
                    p = p.right;
                }else {
                    break;
                }
            }
        }
        return null;
    }

    public BSTNode getFatherNode(K key){
        BSTNode father = root;
        for(BSTNode p = root;;){
            if(p == null){
                break;
            }
            if(p.getKey().compareTo(key) == 0){
                return father;
            }else if(key.compareTo(p.getKey()) < 0){
                if(p.left != null){
                    father = p;
                    p = p.left;
                }else{
                    break;
                }
            }else{
                if(p.right != null){
                    father = p;
                    p = p.right;
                }else {
                    break;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        set.add(key);
        if(root == null){
            root = new BSTNode(key,value);
            size += 1;
        }else{
            for(BSTNode p = root;;){
                if(key.compareTo(p.getKey()) < 0){
                    if(p.left == null){
                        p.left = new BSTNode(key,value);
                        size += 1;
                        break;
                    }else{
                        p = p.left;
                    }
                }else{
                    if(p.right == null){
                        p.right = new BSTNode(key,value);
                        size += 1;
                        break;
                    }else {
                        p = p.right;
                    }
                }
            }
        }
    }

    @Override
    public Set<K> keySet() {
        return set;
    }

    @Override
    public V remove(K key) {
        BSTNode father = getFatherNode(key);
        if(father == null){
            return null;
        }
        if(father.getKey().equals(key)){
            V res = root.getValue();
            if(root.left == null && root.right == null){
                root = null;
            }else if(root.left == null && root.right != null){
                root = root.right;
            }else if(root.left != null && root.right == null){
                root = root.left;
            }else{
                BSTNode t = root.right;
                for(;t.left != null;t = t.left){}
                t.left = root.left;
                root = root.right;
            }
            size -= 1;
            set.remove(key);
            return res;
        }
        if(father.left != null && father.left.getKey().compareTo(key) == 0 ){
            V res = father.left.getValue();
            if(father.left.left == null && father.left.right == null){
                father.left = null;
            }else if(father.left.left != null && father.left.right == null){
                father.left = father.left.left;
            }else if(father.left.left == null && father.left.right != null){
                father.left = father.left.right;
            }else{
                BSTNode t = father.left.right;
                for(;t.left != null;t = t.left){}
                t.left = father.left.left;
                father.left = father.left.right;
            }
            size -= 1;
            set.remove(key);
            return res;
        }
        if(father.right.getKey().compareTo(key) == 0){
            V res = father.right.getValue();
            if(father.right.left == null && father.right.right == null){
                father.right = null;
            }else if(father.right.left != null && father.right.right == null){
                father.right = father.right.left;
            }else if(father.right.left == null && father.right.right != null){
                father.right = father.right.right;
            }else{
                BSTNode t = father.right.right;
                for(;t.left != null;t = t.left){}
                t.left = father.right.left;
                father.right = father.right.right;
            }
            size -= 1;
            set.remove(key);
            return res;
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if((getFatherNode(key).left.getKey() == key && getFatherNode(key).left.getValue() == value)||
                (getFatherNode(key).right.getKey() == key && getFatherNode(key).right.getValue() == value)){
            remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new MapIterator<K>();
    }

    private class MapIterator<K> implements Iterator<K>{
        private Object[] keys = set.toArray();
        private int count;
        public MapIterator(){
            Arrays.sort(keys);
            count = 0;
        }

        @Override
        public boolean hasNext() {
            if(count < keys.length-1){
                return true;
            }
            return false;
        }

        @Override
        public K next() {
            count++;
            return (K)keys[count-1];
        }
    }
}
