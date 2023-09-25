CREATE DATABASE SCHOOL_DB;
USE SCHOOL_DB;
-- Create the students table
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    age INT,
    email VARCHAR(100),
    create_date datetime default now()
);

-- Create the courses table
CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100)
);

-- Create a third table to represent the relationship between students and courses
CREATE TABLE student_courses (
    student_id INT,
    course_id INT,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

-- Insert sample data into the students table
INSERT INTO students (first_name, last_name, age, email) VALUES
    ('John', 'Doe', 20, 'john.doe@example.com'),
    ('Alice', 'Smith', 22, 'alice.smith@example.com'),
    ('Bob', 'Johnson', 21, 'bob.johnson@example.com'),
    ('Emily', 'Davis', 23, 'emily.davis@example.com');

-- Insert sample data into the courses table
INSERT INTO courses (course_name) VALUES
    ('Mathematics'),
    ('History'),
    ('Computer Science');

-- Insert records into the student_courses table to represent enrollments
INSERT INTO student_courses (student_id, course_id) VALUES
    (1, 1), -- John is enrolled in Mathematics
    (1, 3), -- John is also enrolled in Computer Science
    (2, 2), -- Alice is enrolled in History
    (3, 1), -- Bob is enrolled in Mathematics
    (4, 3); -- Emily is enrolled in Computer Science