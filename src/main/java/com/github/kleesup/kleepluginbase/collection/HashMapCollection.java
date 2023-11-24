package com.github.kleesup.kleepluginbase.collection;

import java.util.*;

public abstract class HashMapCollection<K, V> extends HashMap<K, Collection<V>> {

    public static <K, V> HashMapCollection<K, V> forArrayList(){
        return new HashMapCollection<K, V>() {
            @Override
            protected ArrayList<V> buildCollection() {
                return new ArrayList<>();
            }
        };
    }

    public static <K, V> HashMapCollection<K, V> forHashSet(){
        return new HashMapCollection<K, V>() {
            @Override
            protected HashSet<V> buildCollection() {
                return new HashSet<>();
            }
        };
    }

    public HashMapCollection(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public HashMapCollection(int initialCapacity) {
        super(initialCapacity);
    }

    public HashMapCollection() {
    }

    public HashMapCollection(Map<? extends K, ? extends Collection<V>> m) {
        super(m);
    }

    public boolean add(K key, V value){
        Collection<V> collection = get(key);
        if(collection == null){
            collection = buildCollection();
            put(key, collection);
        }
        return collection.add(value);
    }

    protected abstract Collection<V> buildCollection();

}
