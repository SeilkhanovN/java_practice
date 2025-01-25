package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        try{
            Class.forName("org.postgresql.Driver");

            // Replace these with your PostgreSQL connection details
            String url = "jdbc:postgresql://localhost:5432/jdbc_demo_database";
            String user = "postgres";
            String password = "postgres";

            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                // Create table processor and create table
                TableProcessor tableProcessor = new TableProcessor(connection);
                tableProcessor.createTable(Person.class);

                // Create entity manager and insert a person
                EntityManager entityManager = new EntityManager(connection);
                Person person = new Person("John", "Doe", 30);
                entityManager.insert(person);

                System.out.println("Successfully created table and inserted person!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}