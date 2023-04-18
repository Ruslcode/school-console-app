package com.foxminded.university.components;

import com.foxminded.university.models.AssigmentCourse;
import com.foxminded.university.models.Course;
import com.foxminded.university.repositories.AssignmentRepository;
import com.foxminded.university.repositories.CourseRepository;
import com.foxminded.university.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private String ERROR_MASSAGE_ADD_COURSE = "Could not add course to student!\n " + "Check student & course existing or course all ready exist";
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Transactional
    public void deleteStudentById(int studentID) {
        studentRepository.deleteById(Long.valueOf(studentID));
        assignmentRepository.deleteAllByStudentID(studentID);
    }

    @Transactional
    public List<Course> addCourse(int studentId, int courseId) throws SQLException {
        var student = studentRepository.findByID(studentId);
        var course = courseRepository.findByIndex(courseId);
        var assignmentCourses = assignmentRepository.findByStudentID(studentId);
        int groupID = assignmentCourses.get(0).getGroupID();

        if (course.isPresent() && student.isPresent()) {
            List<AssigmentCourse> existsCourse = assignmentCourses.stream().filter(c -> c.getCourseID() == courseId).collect(Collectors.toList());
            if (existsCourse.isEmpty()) {
                AssigmentCourse tempAssigment = new AssigmentCourse();
                tempAssigment.setCourseID(courseId);
                tempAssigment.setStudentID(studentId);
                tempAssigment.setGroupID(groupID);
                assignmentRepository.save(tempAssigment);
                return courseRepository.findCoursesByStudentId(studentId);
            }
        }
        throw new RuntimeException(ERROR_MASSAGE_ADD_COURSE);
    }
}
