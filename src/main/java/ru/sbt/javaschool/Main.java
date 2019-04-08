package ru.sbt.javaschool;

import ru.sbt.javaschool.fixedThreadPool.FixedThreadPool;
import ru.sbt.javaschool.scalableThreadPool.ScalableThreadPool;

import java.util.concurrent.TimeUnit;

public class Main {
    static final int MIN_COUNT_THREAD = 10;
    static final int MAX_COUNT_THREAD = 12;

    public static void main(String[] args) {
//-----------------------------------------------------------------------
/*        FixedThreadPool fixedThreadPool = new FixedThreadPool(MIN_COUNT_THREAD);
        fixedThreadPool.start();

        //ждем для наглядности вывода в консоли
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < MAX_COUNT_THREAD; i++) {
            fixedThreadPool.execute(new SomeTask(i));
        }

        //ждем для нагляднсти вывода в консоли
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fixedThreadPool.stop();*/
//-----------------------------------------------------------------------

//-----------------------------------------------------------------------
        ScalableThreadPool scalableThreadPool = new ScalableThreadPool(MIN_COUNT_THREAD, MAX_COUNT_THREAD);
        scalableThreadPool.start();

        //ждем для нагляднсти вывода в консоли
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < MIN_COUNT_THREAD; i++) {
            scalableThreadPool.execute(new SomeTask(i));
        }

        //ждем для нагляднсти вывода в консоли
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = MIN_COUNT_THREAD; i < 30; i++) {
            scalableThreadPool.execute(new SomeTask(i));
        }

        //ждем для нагляднсти вывода в консоли
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scalableThreadPool.stop();
//-----------------------------------------------------------------------
    }
}
