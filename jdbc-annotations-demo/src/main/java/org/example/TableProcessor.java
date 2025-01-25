package org.example;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableProcessor {
    private final Connection connection;

    public TableProcessor(Connection connection) {
        this.connection = connection;
    }

    public void createTable(Class<?> clazz) throws SQLException {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not marked with @Table annotation");
        }

        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(table.title())
                .append(" (");

        Field[] fields = clazz.getDeclaredFields();
        boolean first = true;

        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                if (!first) {
                    query.append(", ");
                }
                query.append(column.name())
                        .append(" ")
                        .append(getSqlType(field.getType()));

                if (column.primaryKey()) {
                    query.append(" PRIMARY KEY");
                }
                first = false;
            }
        }

        query.append(")");

        try (Statement statement = connection.createStatement()) {
            statement.execute(query.toString());
        }
    }

    private String getSqlType(Class<?> type) {
        if (type == Long.class || type == long.class) {
            return "BIGINT";
        } else if (type == Integer.class || type == int.class) {
            return "INTEGER";
        } else if (type == String.class) {
            return "VARCHAR(255)";
        } else {
            return "VARCHAR(255)";
        }
    }
}