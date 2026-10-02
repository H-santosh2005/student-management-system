package com.sms.service;

import com.sms.exception.DuplicateEmailException;
import com.sms.exception.StudentNotFoundException;
import com.sms.model.Student;
import com.sms.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    // Constructor injection (dependency injection)
    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student register(Student student) {
        student.setId(null);
        student.setEmail(student.getEmail().trim());
        if (repository.existsByEmail(student.getEmail())) {
            throw new DuplicateEmailException(student.getEmail());
        }
        return repository.save(student);
    }

    @Override
    public Student update(Long id, Student student) {
        Student existing = getById(id);
        String email = student.getEmail().trim();
        if (repository.existsByEmailAndIdNot(email, id)) {
            throw new DuplicateEmailException(email);
        }
        existing.setName(student.getName());
        existing.setEmail(email);
        existing.setPhone(student.getPhone());
        existing.setCourse(student.getCourse());
        existing.setAge(student.getAge());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAll() {
        return repository.findAll(org.springframework.data.domain.Sort.by("id"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAll();
        }
        return repository.search(keyword.trim());
    }
}
