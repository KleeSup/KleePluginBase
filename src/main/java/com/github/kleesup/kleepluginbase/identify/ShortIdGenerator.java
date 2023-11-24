package com.github.kleesup.kleepluginbase.identify;

import java.util.UUID;

/**
 * @author KleeSup
 * @version 1.0
 * @since 1.3
 */
public class ShortIdGenerator {

    private static final ShortIdGenerator sizedFive = new ShortIdGenerator(5);
    private static final ShortIdGenerator sizedTen = new ShortIdGenerator(10);
    public static String generateFiveSized(){
        return sizedFive.generate();
    }
    public static String generateTenSized(){
        return sizedTen.generate();
    }

    private final int size;
    public ShortIdGenerator(int size){
        if(size < 0 || size > 16) throw new IllegalArgumentException(
                "Size cannot be smaller than 0 or bigger than 16");
        this.size = size;
    }

    public String generate(){
        return UUID.randomUUID().toString()
                .replace("-", "").substring(0,size);
    }

    public int getSize(){
        return size;
    }

}
