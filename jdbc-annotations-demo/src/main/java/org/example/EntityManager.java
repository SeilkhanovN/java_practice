package org.example;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    public void insert(Object entity) throws SQLException, IllegalAccessException {
        Class<?> clazz = entity.getClass();
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not marked with @Table annotation");
        }

        List<String> columnNames = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && !column.primaryKey()) {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (value != null) {
                    columnNames.add(column.name());
                    values.add(value);
                }
            }
        }

        String sql = buildInsertQuery(table.title(), columnNames);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.size(); i++) {
                stmt.setObject(i + 1, values.get(i));
            }
            stmt.executeUpdate();
        }
    }

    private String buildInsertQuery(String tableName, List<String> columnNames) {
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (int i = 0; i < columnNames.size(); i++) {
            if (i > 0) {
                columns.append(", ");
                placeholders.append(", ");
            }
            columns.append(columnNames.get(i));
            placeholders.append("?");
        }

        return String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, columns, placeholders);
    }
}