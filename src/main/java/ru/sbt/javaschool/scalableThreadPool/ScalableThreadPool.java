package ru.sbt.javaschool.scalableThreadPool;

import ru.sbt.javaschool.interfaces.ThreadPool;

public class ScalableThreadPool implements ThreadPool {

    private int minCountThread;
    private int maxCountThread;

    public ScalableThreadPool(int minCountThread, int maxCountThread) {
        this.minCountThread = minCountThread;
        this.maxCountThread = maxCountThread;
    }

    @Override
    public void start() {

    }

    @Override
    public void execute(Runnable runnable) {

    }
}
