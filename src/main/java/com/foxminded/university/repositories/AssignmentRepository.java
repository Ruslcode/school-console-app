package com.foxminded.university.repositories;

import com.foxminded.university.models.AssigmentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<AssigmentCourse, Long> {

    List<AssigmentCourse> findByStudentID (int studentID);

    void deleteAllByStudentID (int studentID );
}
