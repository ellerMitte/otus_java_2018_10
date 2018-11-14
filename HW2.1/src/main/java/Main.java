import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor on 14.11.18.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Benchmark benchmark = new Benchmark();
        benchmark.measure(Test::new, "Test class");
        benchmark.measure(() -> new Integer(0), "Integer");
        benchmark.measure(String::new, "Empty String with pool");
        benchmark.measure(() -> new String(new byte[0]), "Empty String without pool");
        benchmark.measure(() -> new String(new char[0]), "Empty String (java8 or java9)");
        benchmark.measure(() -> {
            List<Integer> integerList = new ArrayList();
            for (int i = 0; i<10; i++)
                integerList.add(new Integer(0));
            return integerList;
        }, "ArrayList<Integer>[10]");
        /*benchmark.measure(() -> {
            List<Integer> integerList = new ArrayList();
            for (int i = 0; i<100; i++)
                integerList.add(new Integer(0));
            return integerList;
        }, "ArrayList<Integer>[100]");*/
    }
}
