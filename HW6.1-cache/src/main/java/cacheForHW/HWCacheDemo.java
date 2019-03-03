package cacheForHW;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class HWCacheDemo {

    public static void main(String[] args) throws InterruptedException {
        new HWCacheDemo().demo();
    }

    private void demo() throws InterruptedException {
        HwCache<Integer, Integer> cache = new MyCache<>();
        HwListener<Integer, Integer> listener =
                (key, value, action) -> System.out.println("key:" + key + ", value:" + value + ", action:" + action);
        cache.addListener(listener);
        cache.put(1, 1);

        cache.remove(1);
        cache.removeListener(listener);

    }
}
