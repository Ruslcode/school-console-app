package com.foxminded.university.repositories;

import com.foxminded.university.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGroupName(String groupName);

    @Query(value = "select university_groups.group_id, group_name, t.student_number  from university_groups \n" +
            "join (select distinct  group_id , (round(count(student_id)/3 )) as student_number \n" +
            "from course_assignment\n" +
            "group by group_id \n" +
            "order by group_id) t on t.group_id = university_groups.group_id \n" +
            "where t.student_number <= ?;",
            nativeQuery = true)
    List<Group> findWithEqualOrLessStudents(@Param("student_number") int quantity);
}

