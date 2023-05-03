package com.foxminded.university.repositories;

import com.foxminded.university.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByFirstName(String firstName);

    @Query(value = "select * from students where first_name like ? and last_name like ? limit 1;", nativeQuery = true)
    Optional<Student> findStudentByFirstNameAndLastNameLimit1 (String firstName, String  lastName);

    @Query(value = "\n" +
            "select t.student_id, first_name, last_name, g.group_name from course_assignment t\n" +
            "left join university_groups g on g.group_id = t.group_id\n" +
            "left join students s on s.student_id = t.student_id\n" +
            "where g.group_name = (select group_name from course_assignment t\n" +
            "left join university_groups g on g.group_id = t.group_id \n" +
            "where t.student_id = ?\n" +
            "group by g.group_name)\n" +
            "group by t.student_id, g.group_name, student_id, first_name, last_name;\n", nativeQuery = true)
    List<Student> findClassMates(@Param("student_id") int studentID);

    Optional<Student> findByID(int ID);
}
