package ru.sbt.javaschool.fixedThreadPool;

import ru.sbt.javaschool.interfaces.ThreadPool;

import java.util.LinkedList;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {

    private int countThread;
    private Queue<Runnable> queue = new LinkedList<>();
    MyThread[] thread;


    public FixedThreadPool(int countThread) {
        this.countThread = countThread;
        thread = new MyThread[countThread];
    }

    @Override
    public void start() {
        for (int i = 0; i < countThread; i++) {
            thread[i] = new MyThread();
            thread[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (queue) {
            queue.add(runnable);
            queue.notify();
        }
    }



    private class MyThread extends Thread {
        @Override
        public void run() {
            Runnable runnable;
            while (!Thread.interrupted()) {
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            System.err.println(this.getName() + " waiting!");
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                return;
                            }

                        }
                        runnable = queue.remove();
                    }
                    runnable.run();
            }
        }
    }
}
