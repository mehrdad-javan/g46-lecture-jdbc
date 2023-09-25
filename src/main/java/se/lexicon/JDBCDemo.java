package se.lexicon;

import java.sql.*;

public class JDBCDemo {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_db", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select student_id, first_name, last_name from students");
            // executeQuery used for SELECT Queries.
            // executeUpdate used for INSERT, DELETE, UPDATE
            while (resultSet.next()) {
                // access the columns data
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                System.out.println(studentId + "," + firstName + "," + lastName );
            }


        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
        }

    }

}
