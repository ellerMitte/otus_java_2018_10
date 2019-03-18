/**
 * @author Igor on 13.03.19.
 */
public class WaitExample {
    private volatile String thr = "t2";

    public static void main(String[] args) {
        WaitExample exercise = new WaitExample();
        new Thread(() -> exercise.action("t1")).start();
        new Thread(() -> exercise.action("t2")).start();
    }

    private void action(String message) {
        boolean inc = false;
        int i = 1;
        StringBuilder sb = new StringBuilder(message + ":");
        while (true) {
            if (thr.equals(message)) {
                wait(this);
            } else {
                inc = (i == 10 || i == 1) != inc;
                sb.append(" ").append(inc ? i++ : i--);
                System.out.println(sb.toString());
                thr = message;
                sleep(1000);
                notifyAll();
            }
        }
    }

    private static void wait(Object object) {
        try {
            object.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
