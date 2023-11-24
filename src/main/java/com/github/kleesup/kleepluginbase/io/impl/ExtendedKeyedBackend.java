package com.github.kleesup.kleepluginbase.io.impl;

import com.github.kleesup.kleepluginbase.io.KeyedBackend;

/**
 * An extended implementation of {@link KeyedBackend} which adds some type casting methods.
 */
public interface ExtendedKeyedBackend<K, V> extends KeyedBackend<K, V> {

    default byte getByte(K key){
        return (byte)get(key);
    }
    default short getShort(K key){
        return (short)get(key);
    }
    default int getInt(K key){
        return (int)get(key);
    }
    default long getLong(K key){
        return (long)get(key);
    }
    default float getFloat(K key){
        return (float)get(key);
    }
    default double getDouble(K key){
        return (double)get(key);
    }
    default char getChar(K key){
        return (char)get(key);
    }
    default String getString(K key){
        return (String) get(key);
    }

}
