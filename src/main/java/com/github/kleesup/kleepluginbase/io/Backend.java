package com.github.kleesup.kleepluginbase.io;

/**
 * Simple interface for IO performing classes.
 * <br>Created on 23.11.2023</br>
 * @author KleeSup
 * @version 1.0
 * @since 1.0
 */
public interface Backend {

    /**
     * Saves changes to the backend.
     */
    void save();

    /**
     * Closes the backend.
     */
    default void close(){
        save();
    }

}
