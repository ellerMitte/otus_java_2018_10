package dbservice;

import dbservice.Exceptions.IdFieldException;
import dbservice.annotations.Id;
import executor.DbExecutor;
import executor.DbExecutorImpl;
import helpers.ReflectionHelper;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Igor on 14.02.19.
 */
public class DbServiceImpl<T> implements DbService<T> {
    private final DataSource dataSource;

    public DbServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(T objectData) {
        String sql;
        Field idField;
        try {
            idField = getIdField(objectData.getClass());
        } catch (IdFieldException e) {
            System.out.println(e.getMessage());
            return;
        }
        String idFieldName = idField.getName();
        List<String> params = getParams(objectData, idFieldName);
        long id = (long) ReflectionHelper.getFieldValue(objectData, idFieldName);
        if (load(id, objectData.getClass()) == null) {
            sql = getSqlQuery("insert into %s (%s) values (%s)", objectData, idFieldName);
        } else {
            sql = getSqlQuery("update %s set (%s) = (%s) where id = ?", objectData, idFieldName);
            params.add(String.valueOf(id));
        }

        try (Connection connection = dataSource.getConnection()) {
            DbExecutor<T> executor = new DbExecutorImpl<>(connection);
            executor.insertRecord(sql, params);
            connection.commit();
            System.out.println("saved " + objectData.getClass().getSimpleName());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public <T> T load(long id, Class<T> clazz) {
        Field idField;
        try {
            idField = getIdField(clazz);
        } catch (IdFieldException e) {
            System.out.println(e.getMessage());
            return null;
        }
        try (Connection connection = dataSource.getConnection()) {
            DbExecutor<T> executor = new DbExecutorImpl<>(connection);
            String sql = "select * from %s where id  = ?";
            sql = String.format(sql, clazz.getSimpleName());
            Optional<T> resultObject = executor.selectRecord(sql, id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        Object[] args = Arrays.stream(clazz.getDeclaredFields())
                                .map(field -> {
                                    try {
                                        return resultSet.getObject(field.equals(idField) ? "id" : field.getName());
                                    } catch (SQLException e) {
                                        System.out.println("field " + field.getName() + " not found.");
                                        return null;
                                    }
                                }).toArray();
                        return ReflectionHelper.instantiate(clazz, args);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });
            return resultObject.orElse(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private Field getIdField(Class clazz) throws IdFieldException {
        Field idField = ReflectionHelper.getFieldWithAnnotation(clazz, Id.class);
        if (idField == null) {
            throw new IdFieldException("Field with @ID not found in " + clazz);
        }
        return idField;
    }

    private List<String> getParams(T objectData, String idFieldName) {
        return Arrays.stream(ReflectionHelper.getFields(objectData))
                .filter(field -> !idFieldName.equals(field.getName()))
                .map(field -> String.valueOf(ReflectionHelper.getFieldValue(objectData, field)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String getSqlQuery(String sql, T objectData, String idFieldName) {
        String[] fieldsArray = Arrays.stream(ReflectionHelper.getFieldsName(objectData))
                .filter(field -> !idFieldName.equals(field))
                .toArray(String[]::new);
        String fields = Arrays.toString(fieldsArray).replaceAll("[\\[\\]]", "");
        sql = String.format(sql, objectData.getClass().getSimpleName(), fields, fields.replaceAll("\\w+", "?"));
        return sql;
    }

}


