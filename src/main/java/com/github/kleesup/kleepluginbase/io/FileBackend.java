package com.github.kleesup.kleepluginbase.io;

import com.github.kleesup.kleepluginbase.KleeHelper;

import java.io.File;

public interface FileBackend extends Backend {

    File getFile();

    @Override
    default void save(){
        KleeHelper.createFileIfNotExist(getFile());
    }
}
