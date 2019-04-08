package ru.sbt.javaschool.fixedThreadPool;

import ru.sbt.javaschool.interfaces.ThreadPool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {

    private int countThread;
    private Queue<Runnable> queue = new LinkedList<>();
    private static final String monitor = "";
    private ArrayList<MyThread> thread;


    public FixedThreadPool(int countThread) {
        this.countThread = countThread;
        thread = new ArrayList<>(countThread);
    }

    @Override
    public void start() {
        for (int i = 0; i < countThread; i++) {
            thread.add(new MyThread());
            System.err.println("add new  " + thread.get(thread.size() - 1).getName());
        }
        for (MyThread myThread : thread) {
            myThread.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (monitor) {
            queue.add(runnable);
            monitor.notify();
        }
    }

    public void stop() {
        while (true) {
            int countWaitThread = 0;
            for (MyThread myThread : thread) {
                if (myThread.getState() == Thread.State.WAITING) {
                    countWaitThread++;
                }
            }
            if (countWaitThread == countThread){
                interruptThreads();
                break;
            }
                    }

    }

    private void interruptThreads() {
        for (MyThread myThread : thread) {
            myThread.interrupt();
        }
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            Runnable runnable;
            while (!Thread.interrupted()) {
                synchronized (monitor) {
                    while (queue.isEmpty()) {
                        System.err.println(this.getName() + " waiting!");
                        try {
                            monitor.wait();
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
