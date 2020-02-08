package com.udacity.course3.lesson1.exercise1.exercise1;

import java.sql.*;

import org.flywaydb.core.Flyway;

public class Application {

    public static void main(String[] args) {
        // STEP 1: Create the JDBC URL for JDND-C3 database

        String basicURL = "jdbc:mysql://127.0.0.1:3306/jdnd_c3?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String completeURL = basicURL + "&user=root&password=root";

        try {

/**            Class.forName("com.mysql.jdbc.Driver"); */
            Class.forName("com.mysql.cj.jdbc.Driver");

        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // STEP 2: Setup and Run Flyway migration that creates the member table using its Java API
        // https://flywaydb.org/getstarted/firststeps/api#integrating-flyway
        // Note the above link talks about connecting to H2 database, for this exercise, MySQL is used. Adapt the code accordingly.

        Flyway flyway = Flyway.configure().dataSource(basicURL, "root", "root").load();

        // Start the migration
        flyway.migrate();

        // STEP 3: Obtain a connection to the JDND-C3 database


        try {
            try (Connection conn = DriverManager.getConnection(completeURL)) {
                System.out.println("Connected to database");
                System.out.println("Connected to "+conn.getMetaData().getDatabaseProductName());

                // STEP 4: Use Statement to INSERT 2 records into the member table
                // NOTE: The member table is created using Flyway by placing the migration file in src/main/resources/db/migration

                String query1 = "INSERT INTO member (first_name, last_name, age, gender, balance) VALUES ('John', 'Doe', 53, 'MALE', 0)";
                String query2 = "INSERT INTO member (first_name, last_name, age, gender, balance) VALUES ('Mary', 'Tucker', 42, 'FEMALE', 100)";

                Statement stmt = conn.createStatement();
                stmt.executeUpdate(query1);
                stmt.executeUpdate(query2);

                // STEP 5: Read ALL the rows from the member table and print them here

                ResultSet rs = stmt.executeQuery("SELECT id, first_name, last_name, age, gender, balance FROM member");

                while (rs.next()) {
                    int memberID = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    int age = rs.getInt("age");
                    String gender = rs.getString("gender");
                    int balance = rs.getInt("balance");
                    System.out.println("ID: " + memberID + " First Name: " + firstName + " Last Name: " + lastName  + " Age: " + age + " Gender: " + gender + " Balance: " + balance);
                }

                // STEP 6: verify that all inserted rows have been printed
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }






    }

}