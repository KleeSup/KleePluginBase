package com.github.kleesup.kleepluginbase.io.impl;

import com.github.kleesup.kleepluginbase.Checker;
import com.github.kleesup.kleepluginbase.io.FileBackend;
import com.github.kleesup.kleepluginbase.io.KeyedBackend;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * An implementation of {@link FileBackend} which uses Bukkits {@link YamlConfiguration} to
 * serialize and deserialize data.
 */
public class YamlFileBackend extends YamlConfiguration implements FileBackend, KeyedBackend<String, Object> {

    private final File file;
    public YamlFileBackend(File file){
        Checker.requireNonNull(file, "File cannot be null!");
        this.file = file;
        if(file.exists()){
            try {
                load(file);
            } catch (IOException | InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void save() {
        try {
            save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public Object remove(String key) {
        Object old = get(key);
        set(key, null);
        return old;
    }
}
