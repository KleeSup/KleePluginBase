package com.github.kleesup.kleepluginbase.collection;

import java.util.HashMap;
import java.util.Map;

public class DoubleKeyHashMap<K1, K2, V> {

    private final HashMap<K1, HashMap<K2, V>> elements = new HashMap<>();
    private int size;

    public V get(K1 key1, K2 key2){
        HashMap<K2, V> inner = elements.get(key1);
        return inner != null ? inner.get(key2) : null;
    }

    public V getOrDefault(K1 key1, K2 key2, V defaultValue){
        V val = get(key1, key2);
        return val != null ? val : defaultValue;
    }

    public V put(K1 key1, K2 key2, V value){
        if(value == null)return remove(key1,key2);
        HashMap<K2, V> inner = elements.get(key1);
        if(inner == null){
            inner = new HashMap<>();
            elements.put(key1,inner);
        }
        size++;
        return inner.put(key2, value);
    }
    public V putIfAbsent(K1 key1, K2 key2, V value){
        if(!containsFullKeys(key1, key2)){
            return put(key1, key2, value);
        }
        return null;
    }

    public void putAll(K1 key1, Map<K2, V> values){
        HashMap<K2, V> inner = elements.get(key1);
        if(inner != null)inner.putAll(values);
    }

    public V remove(K1 key1, K2 key2){
        HashMap<K2, V> inner = elements.get(key1);
        if(inner != null){
            size--;
            V old = inner.remove(key2);
            if(inner.isEmpty()){
                elements.remove(key1);
            }
            return old;
        }
        return null;
    }

    public boolean containsFirstKey(K1 key1){
        return elements.containsKey(key1);
    }

    public boolean containsFullKeys(K1 key1, K2 key2){
        HashMap<K2, V> inner = elements.get(key1);
        return inner != null && inner.containsKey(key2);
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int getSize(){
        return size;
    }

}
