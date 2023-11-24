package com.github.kleesup.kleepluginbase;

public interface Condition {

    static Condition forStaticCheck(boolean staticResult){
        return () -> staticResult;
    }

    boolean check();


}
