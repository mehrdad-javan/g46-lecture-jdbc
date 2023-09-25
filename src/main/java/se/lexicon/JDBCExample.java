package se.lexicon;

import se.lexicon.db.MySQLConnection;
import se.lexicon.model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCExample {

    public static void main(String[] args) {
        ex4();
    }

    // get all students data
    public static void ex1() {

        try {
            Connection connection = MySQLConnection.getConnection(); // how to call a static method?
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from students");
            List<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                int studentId = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int age = resultSet.getInt(4);
                String email = resultSet.getString(5);
                LocalDate createDate = resultSet.getDate(6).toLocalDate();
                Student student = new Student(studentId, firstName, lastName, age, email, createDate);

                studentList.add(student);
            }

            studentList.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get student by id
    public static void ex2() {
        // step 1: create a database connection
        // step 2: create a prepared statement
        // step 3: execute query
        // step 4: get data from result set and add it to java object.
        // step 5: close all the resources

        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from students where student_id = ? ");
        ) {
            int inputId = 1;
            preparedStatement.setInt(1, inputId);
            //preparedStatement.setString(2, "%" + "John" + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Student student = null;
                if (resultSet.next()) {
                    int studentId = resultSet.getInt("student_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    int age = resultSet.getInt("age");
                    String email = resultSet.getString("email");
                    LocalDate createDate = resultSet.getDate("create_date").toLocalDate();
                    student = new Student(studentId, firstName, lastName, age, email, createDate);
                }

                System.out.println(student);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } // it will close -> first resultSet, preparedStatement, connection


    }

    // create a student
    public static void ex3() {
        Student student = new Student("Test2", "Test2", 22, "test2@test.se");
        String query = "INSERT INTO STUDENTS(first_name, last_name, age, email) VALUES(?, ?, ?, ?)";
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {

            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getEmail());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student created successfully!");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedStudentId = generatedKeys.getInt(1);
                    System.out.println("generatedStudentId = " + generatedStudentId);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // create student + assign student to the course
    public static void ex4() {

        Student student = new Student("Test10", "Test10", 33, "test10@test.se");
        String insertQueryForStudent = "INSERT INTO STUDENTS(first_name, last_name, age, email) VALUES(?, ?, ?, ?)";
        String insertQueryForStudentsCourses = "INSERT INTO STUDENTS_COURSES(student_id, course_id) values(?, ?)";

        Connection connection = MySQLConnection.getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(insertQueryForStudent, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {

            connection.setAutoCommit(false);

            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getEmail());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student created successfully!");
            } else {
                connection.rollback();
                throw new RuntimeException("Insert Operation in (student) table failed!");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedStudentId = generatedKeys.getInt(1);
                    System.out.println("generatedStudentId = " + generatedStudentId);


                    int courseId = 1;
                    try (PreparedStatement preparedStatementForStudentsCourses = connection.prepareStatement(insertQueryForStudentsCourses)) {
                        preparedStatementForStudentsCourses.setInt(1, generatedStudentId);
                        preparedStatementForStudentsCourses.setInt(2, courseId); // assume course id is valid!

                        int insertedRows = preparedStatementForStudentsCourses.executeUpdate(); // app throws exception
                        if (insertedRows > 0) {
                            System.out.println("Assigning course to student is done successfully!");
                        } else {
                            connection.rollback();
                            throw new RuntimeException("Insert Operation in (students_courses) table failed!");

                        }

                    }


                }
            }

            connection.setAutoCommit(true);


        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null){
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            }
        }

        // students
        // students_courses
    }

}
