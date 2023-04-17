package com.foxminded.university.repositories;


import com.foxminded.university.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseName(String courseName);

    Optional<Course> findByIndex (int index);

    @Query(value = "select c.course_id, c.course_name, c.course_description from course_assignment t\n" +
            "left join courses c on c.course_id = t.course_id\n" +
            "where t.student_id = ?;", nativeQuery = true)
    List<Course> findCoursesByStudentId (@Param("student_id") int studentID);


}