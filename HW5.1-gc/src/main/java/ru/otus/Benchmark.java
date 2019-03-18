package ru.otus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor on 15.03.19.
 */
class Benchmark implements BenchmarkMBean {
    private volatile int size = 0;
    private final int loopCounter;
    private final List<String> list = new ArrayList<>();

    Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    void run() throws InterruptedException {
        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;
            for (int i = 0; i < local; i++) {
                list.add(new String(new char[10]));
                if (i % 10000 == 0) {
                    Thread.sleep(50);
                }
            }
            for (int i = 0; i < size / 2; i++) {
                list.remove(list.size() - 1);
            }
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }

}
