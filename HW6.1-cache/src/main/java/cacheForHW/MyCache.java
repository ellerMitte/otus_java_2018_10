package cacheForHW;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    private static final String ACTION_PUT = "put";
    private static final String ACTION_REMOVE = "remove";
    private static final String ACTION_GET = "get";

    private final HashMap<K, SoftReference<V>> elements = new HashMap<>();
    private List<HwListener> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        elements.put(key, new SoftReference<>(value));
        notifyAllListeners(key, value, ACTION_PUT);
    }

    @Override
    public void remove(K key) {
        SoftReference<V> ref = elements.get(key);
        if (ref != null) {
            elements.remove(key);
            notifyAllListeners(key, ref.get(), ACTION_REMOVE);
        }
    }

    @Override
    public V get(K key) {
        Optional<SoftReference<V>> optional = Optional.ofNullable(elements.get(key));
        return optional.map(ref -> {
            notifyAllListeners(key, ref.get(), ACTION_GET);
            return ref.get();
        }).orElse(null);
    }

    @Override
    public void addListener(HwListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener listener) {
        listeners.remove(listener);
    }

    private void notifyAllListeners(K key, V value, String action) {
        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }
}
