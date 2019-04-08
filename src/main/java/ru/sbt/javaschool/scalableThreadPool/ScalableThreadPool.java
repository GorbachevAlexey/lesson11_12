package ru.sbt.javaschool.scalableThreadPool;

import ru.sbt.javaschool.interfaces.ThreadPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class ScalableThreadPool implements ThreadPool {

    private int minCountThread;
    private int maxCountThread;
    private Queue<Runnable> queue = new LinkedList<>();
    private ArrayList<MyThread> thread;
    private boolean isStart = false;
    private static final String monitor = "";

    public ScalableThreadPool(int minCountThread, int maxCountThread) {
        this.minCountThread = minCountThread;
        this.maxCountThread = maxCountThread;
        thread = new ArrayList<>(maxCountThread);
    }

    @Override
    public void start() {
        for (int i = 0; i < minCountThread; i++) {
            thread.add(new MyThread());
            System.err.println("add new " + thread.get(thread.size() - 1).getName());
        }
        for (MyThread myThread : thread) {
            myThread.start();
        }
        isStart = true;
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (monitor) {
            queue.add(runnable);
            checkAddThread();
            monitor.notify();
        }
    }

    /*public void stop() {
        while (true) {
            boolean allWaitThread = true;
            for (MyThread myThread : thread) {
                if (myThread.getState() != Thread.State.WAITING)
                    allWaitThread = false;
            }
            if (!allWaitThread) {
                interruptThreads();
                break;
            }
        }
    }

    private void interruptThreads() {
        for (MyThread myThread : thread) {
            myThread.interrupt();
        }
    }*/

    private void checkAddThread() {
        //если потоков меньше, чем макисмально разрешенных и задач больше чем доступных потоков,
        //то добавляю еще поток и запускаю
        if ((maxCountThread > thread.size()) && (thread.size() < queue.size())) {
            thread.add(new MyThread());
            if (isStart) thread.get(thread.size() - 1).start();
            System.err.println("add new " + thread.get(thread.size() - 1).getName());

        }
    }

    private void checkRemoveThread(Thread th) {
        //если потоков больше, чем минимально разрешенных и задач меньше чем доступных потоков,
        //то останавливаю и удаляю поток
        if ((minCountThread < thread.size()) && (thread.size() > queue.size())) {
            System.err.println("remove " + th.getName());
            th.interrupt();
            thread.remove(th);
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
                            checkRemoveThread(this);
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
