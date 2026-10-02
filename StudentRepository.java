package com.sms.repository;

import com.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    @Query("""
            SELECT s FROM Student s
            WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :kw, '%'))
               OR LOWER(s.email) LIKE LOWER(CONCAT('%', :kw, '%'))
               OR LOWER(s.course) LIKE LOWER(CONCAT('%', :kw, '%'))
               OR CAST(s.id AS string) = :kw
            ORDER BY s.id
            """)
    List<Student> search(@Param("kw") String keyword);
}
