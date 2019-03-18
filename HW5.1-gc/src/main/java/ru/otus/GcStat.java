package ru.otus;

/**
 * @author Igor on 15.03.19.
 */
public class GcStat {
    private final String name;
    private int quantity;
    private long time;

    public GcStat(String name) {
        this.name = name;
    }

    void increase(long time) {
        quantity++;
        this.time += time;
    }

    @Override
    public String toString() {
        return "GcStat{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", time=" + time +
                '}';
    }
}
