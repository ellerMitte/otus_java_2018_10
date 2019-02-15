package executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Igor on 14.02.19.
 */
public interface DbExecutor<T> {

    void insertRecord(String sql, List<String> params) throws SQLException;
    Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException;
}
