package ru.sbt.javaschool;

import ru.sbt.javaschool.fixedThreadPool.FixedThreadPool;

import java.util.concurrent.TimeUnit;

public class Main {
    static final int COUNT_THREAD = 10;
    public static void main(String[] args) {
        FixedThreadPool fixedThreadPool = new FixedThreadPool(COUNT_THREAD);
        fixedThreadPool.start();

        //wait start threads
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < COUNT_THREAD; i++) {
            fixedThreadPool.execute(new SomeTask(i));
        }

        //wait
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fixedThreadPool.stop();
    }
}
