package dbservice;

/**
 * @author Igor on 14.02.19.
 */
public interface DbService<T> {

    void save(T objectData);

    <T> T load(long id, Class<T> clazz);
}
