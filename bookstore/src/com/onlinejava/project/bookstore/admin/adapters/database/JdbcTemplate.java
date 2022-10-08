package com.onlinejava.project.bookstore.admin.adapters.database;

import com.onlinejava.project.bookstore.common.domain.entity.Grade;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.function.Functions.ThrowableBiFunction;
import com.onlinejava.project.bookstore.core.function.Functions.ThrowableFunction;
import com.onlinejava.project.bookstore.core.util.StringUtils;
import com.onlinejava.project.bookstore.core.util.reflect.ReflectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.onlinejava.project.bookstore.core.function.Functions.unchecked;

@Bean
public class JdbcTemplate {

    public <T> Optional<T> get(String sql, Class<T> clazz) {
        return executeQuery(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }
            T object = getObjectFromRS(rs, clazz);
            return Optional.ofNullable(object);
        });
    }

    public <T> List<T> list(String sql, Class<T> clazz) {
        return executeQuery(sql, rs -> {
            List<T> list = new ArrayList<>();
            while (rs.next()) {
                T object = getObjectFromRS(rs, clazz);
                list.add(object);
            }
            return list;
        });
    }

    public boolean insert(String sql, int expectedCount) {
        return executeUpdate(sql, expectedCount);
    }

    public boolean update(String sql, int expectedCount) {
        return executeUpdate(sql, expectedCount);
    }

    public boolean delete(String sql, int expectedCount) {
        return executeUpdate(sql, expectedCount);
    }

    private boolean executeUpdate(String sql, int expectedCount) {
        Connection connection = DatabaseConnection.getConnection();
        try (Statement stat = connection.createStatement()){
            int count = stat.executeUpdate(sql);
            if (count != expectedCount) {
                connection.rollback();
                return false;
            }
            connection.commit();
            return true;
        } catch (Throwable e) {
            throw new DatabaseException(e);
        }
    }
    
    public boolean execute(String sql) {
        Connection connection = DatabaseConnection.getConnection();
        try (Statement stat = connection.createStatement()){
            return stat.execute(sql);
        } catch (Throwable e) {
            throw new DatabaseException(e);
        }
    }

    private <T> T executeQuery(String sql, ThrowableFunction<ResultSet, T> function) {
        try (Statement stat = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stat.executeQuery(sql)){
            return function.apply(rs);
        } catch (Throwable e) {
            throw new DatabaseException(e);
        }
    }

    private <T> T getObjectFromRS(ResultSet rs, Class<T> clazz) throws SQLException {
        T object = ReflectionUtils.newInstance(clazz);
        return setValues(rs, object);
    }

    private static <T> T setValues(ResultSet rs, T object) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1, length = metaData.getColumnCount(); i <= length; i++) {
            String fieldName = StringUtils.camel(metaData.getColumnName(i));
            Class<?> fieldType = ReflectionUtils.getFieldType(object, fieldName);
            int columnType = metaData.getColumnType(i);

            Object value = unchecked(getTypeMapper(columnType, fieldType)).apply(rs, i);
            ReflectionUtils.invokeSetter(object, fieldName, fieldType, value);
        }
        return object;
    }

    private static ThrowableBiFunction<ResultSet, Integer, Object> getTypeMapper(int columnType, Class<?> fieldType) {
        return switch (columnType) {
            case Types.NUMERIC, Types.BIGINT -> (rs, i) -> rs.getInt(i);
            case Types.NVARCHAR, Types.VARCHAR ->
                    fieldType == Grade.class ? (rs, i) -> Grade.valueOf(rs.getString(i)) : (rs, i) -> rs.getString(i);
            case Types.BOOLEAN ->  (rs, i) -> rs.getBoolean(i);
            default -> throw new UnsupportedDatabaseColumnType("Type : " + columnType);
        };
    }
}
