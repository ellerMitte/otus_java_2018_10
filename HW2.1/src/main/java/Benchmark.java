
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Igor on 14.11.18.
 */
public class Benchmark {
    private int size;
    private Object[] array;

    public Benchmark() {
        size = 1_000_000;
    }

    public Benchmark(int size) {
        this.size = size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void measure(Supplier<Object> supplier, String desc, int... sizeArray) throws InterruptedException {

        array = new Object[size];

        long elemSize = getMemChanges(() -> {
            for (int i = 0; i < array.length; i++) {
                array[i] = supplier.get();
            }
        });
        System.out.println(desc + " size: " + Math.round((double) elemSize / array.length) + " bytes");
        if (sizeArray.length != 0) {
            System.out.println("  " + ((List) array[0]).get(0).getClass().getSimpleName() + " size: " + Math.round((double) elemSize / (array.length * sizeArray[0])) + " bytes");
        }

        array = null;

        Thread.sleep(1000); //wait for 1 sec
    }

    private static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static long getMemChanges(Runnable runnable) throws InterruptedException {
        long memBefore = getMem();
        runnable.run();
        long memAfter = getMem();
        return memAfter - memBefore;
    }

}
