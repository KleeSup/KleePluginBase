package com.github.kleesup.kleepluginbase.io.impl;

import com.github.kleesup.kleepluginbase.Checker;
import com.github.kleesup.kleepluginbase.io.FileBackend;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * An implementation of {@link FileBackend} and {@link ExtendedKeyedBackend} which uses
 * {@link Gson} to serialize and deserialize data in Json format.
 */
public class JsonFileBackend implements FileBackend, ExtendedKeyedBackend {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final File _file;
    private final HashMap elements;
    public JsonFileBackend(File file){
        Checker.requireNonNull(file, "File cannot be null!");
        this._file = file;
        HashMap result;
        if(_file.exists()){
            try {
                FileReader reader = new FileReader(_file);
                result = gson.fromJson(reader, HashMap.class);
                reader.close();
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.WARNING,
                        "An exception was thrown while parsing JSON from file "+
                                file.getName(), e);
                result = new HashMap();
            }
        }else{
            result = new HashMap();
        }
        elements = result;
    }

    @Override
    public Object get(Object key){
        return elements.get(key);
    }

    @Override
    public boolean isSet(Object key){
        return elements.containsKey(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void set(Object key, Object value){
        elements.put(key, value);
    }

    @Override
    public Object remove(Object key){
        return elements.remove(key);
    }

    @Override
    public void save() {
        FileBackend.super.save();
        try {
            FileWriter writer = new FileWriter(_file);
            writer.write(gson.toJson(elements));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getFile() {
        return _file;
    }
}
