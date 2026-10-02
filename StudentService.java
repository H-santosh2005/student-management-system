package com.sms.service;

import com.sms.model.Student;

import java.util.List;

/** Service abstraction (OOP: interface). */
public interface StudentService {
    Student register(Student student);
    Student update(Long id, Student student);
    void delete(Long id);
    Student getById(Long id);
    List<Student> getAll();
    List<Student> search(String keyword);
}
