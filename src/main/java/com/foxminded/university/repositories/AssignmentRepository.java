package com.foxminded.university.repositories;

import com.foxminded.university.models.AssigmentCourse;
import com.foxminded.university.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<AssigmentCourse, Long> {

    List<AssigmentCourse> findByStudentID (int studentID);

    void deleteAllByStudentID (int studentID );
}
