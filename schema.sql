-- =====================================================
--  Student Management System - MySQL schema
--  Optional: the app can also auto-create this table
--  (spring.jpa.hibernate.ddl-auto=update).
--  Run with:  mysql -u root -p < database/schema.sql
-- =====================================================

CREATE DATABASE IF NOT EXISTS student_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE student_db;

DROP TABLE IF EXISTS students;

CREATE TABLE students (
    id         BIGINT        NOT NULL AUTO_INCREMENT,
    name       VARCHAR(100)  NOT NULL,
    email      VARCHAR(100)  NOT NULL,
    phone      VARCHAR(15)   NULL,
    course     VARCHAR(100)  NOT NULL,
    age        INT           NOT NULL,
    created_at DATETIME(6)   NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_students_email UNIQUE (email)
) ENGINE=InnoDB;

-- Sample data (optional)
INSERT INTO students (name, email, phone, course, age, created_at) VALUES
 ('Aarav Sharma', 'aarav@example.com', '9876543210', 'Computer Science',       20, NOW()),
 ('Priya Reddy',  'priya@example.com', '9123456780', 'Information Technology', 21, NOW()),
 ('Rahul Verma',  'rahul@example.com', '9988776655', 'Electronics',            22, NOW());
