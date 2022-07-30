package hashmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public void clear() {
        size = 0;
        initialSize = 16;
        loadFactor = 0.75;
        buckets = createTable(initialSize);
        for(int i = 0;i < initialSize;i++){
            buckets[i] = createBucket();
        }
        keys = new HashSet<>();
    }

    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    @Override
    public V get(K key) {
        if(keys.contains(key)){
            int hash = Math.abs(key.hashCode())%initialSize;
            for(Node n:buckets[hash]){
                if(n.key.equals(key)){
                    return n.value;
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
        Node newItem = createNode(key,value);
        if(size / initialSize > loadFactor){
            Collection<Node>[] newTable = createTable(2*initialSize);
            for(int i = 0;i < 2*initialSize;i++){
                newTable[i] = createBucket();
            }
            for(int i = 0;i < initialSize;i++){
                if(buckets[i].size() != 0){
                    for(Node n: buckets[i]){
                        int hash = Math.abs(n.key.hashCode())%(2*initialSize);
                        newTable[hash].add(n);
                    }
                }
            }
            initialSize *= 2;
            buckets = newTable;
        }
        int hashValue = Math.abs(key.hashCode()) % initialSize;
        if(!keys.contains(key)){
            buckets[hashValue].add(newItem);
            keys.add(key);
            size += 1;
        }else {
            for(Node n:buckets[hashValue]){
                if(n.key.equals(key)){
                    n.value = value;
                }
            }
        }
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        V res = get(key);
        int hashValue = Math.abs(key.hashCode()) % initialSize;
        Node removeItem = new Node(key,res);
        buckets[hashValue].remove(removeItem);
        keys.remove(key);
        size -= 1;
        return res;
    }

    @Override
    public V remove(K key, V value) {
        if(get(key).equals(value)){
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHSMapIterator<>();
    }

    private class MyHSMapIterator<K> implements Iterator<K>{
        private Object[] array = keys.toArray();
        private int count;

        public MyHSMapIterator(){
            count = 0;
        }
        @Override
        public boolean hasNext() {
            if(count < array.length){
                return true;
            }
            return false;
        }

        @Override
        public K next() {
            count++;
            return (K)array[count-1];
        }
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int initialSize;
    private int size;
    private double loadFactor;
    private Set<K> keys;

    /** Constructors */
    public MyHashMap() {
        size = 0;
        initialSize = 16;
        loadFactor = 0.75;
        buckets = createTable(initialSize);
        keys = new HashSet<>();
        for(int i = 0;i < initialSize;i++){
            buckets[i] = createBucket();
        }
    }

    public MyHashMap(int initialSize) {
        size = 0;
        this.initialSize = initialSize;
        loadFactor = 0.75;
        buckets = createTable(initialSize);
        keys = new HashSet<>();
        for(int i = 0;i < initialSize;i++){
            buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        size = 0;
        this.initialSize = initialSize;
        loadFactor = maxLoad;
        buckets = createTable(initialSize);
        keys = new HashSet<>();
        for(int i = 0;i < initialSize;i++){
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key,value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new HashSet<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }


}
