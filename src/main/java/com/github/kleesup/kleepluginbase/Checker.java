package com.github.kleesup.kleepluginbase;

public class Checker {

    public static void requireNonNull(Object obj, String msg){
        if(obj == null)throw new IllegalArgumentException(msg);
    }

    public static void requireParamCondition(Condition condition, String msg){
        if(!condition.check())throw new IllegalArgumentException(msg);
    }

    public static void doUntilConditionMet(Runnable run, Condition condition){
        while (!condition.check()){
            run.run();
        }
    }

}
