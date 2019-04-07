package ru.sbt.javaschool;

import java.util.concurrent.TimeUnit;

public class SomeTask implements Runnable {

    private int id;

    public SomeTask(int id) {
        this.id = id;
    }


    @Override
    public void run() {
        System.err.println("SomeTask " + id + " - started in " + Thread.currentThread().getName());

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.err.println("SomeTask " + id + " - finished in " + Thread.currentThread().getName());
    }
}
