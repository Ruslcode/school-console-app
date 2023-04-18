package com.foxminded.university;

import com.foxminded.university.components.DataGenerator;
import com.foxminded.university.components.StudentService;
import com.foxminded.university.exceptions.StudentNotFoundException;
import com.foxminded.university.models.AssigmentCourse;
import com.foxminded.university.models.Student;
import com.foxminded.university.repositories.AssignmentRepository;
import com.foxminded.university.repositories.CourseRepository;
import com.foxminded.university.repositories.GroupRepository;
import com.foxminded.university.repositories.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest
@EnableTransactionManagement
@ContextConfiguration(classes = UniversityApplication.class)
class UniversityApplicationTests {
    private int TEST_ID = 9999;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    private Student testStudent = new Student();
    private AssigmentCourse testAssigment = new AssigmentCourse();
    @Autowired
    private EntityManager em;
    @Autowired
    private DataGenerator dataGenerator;

    @Test
    public void studentServiceMustDeleteStudentByStudentIDFromAssignmentAndStudents() {
        testStudent.setFirstName("test student");
        testStudent.setLastName("test last name");
        testStudent.setID(TEST_ID);
        testAssigment.setStudentID(testStudent.getID());
        testAssigment.setCourseID(999);
        testAssigment.setGroupID(666);
        assignmentRepository.save(testAssigment);
        studentRepository.save(testStudent);
        studentService.deleteStudentById(testStudent.getID());
        Exception exception = Assertions.assertThrows(NoResultException.class,
                () -> em.createQuery("select s from Student s where s.ID = :studentID", Student.class).setParameter("studentID", TEST_ID).getSingleResult());
        String exceptedErrorMassage = exception.getMessage();
        String actualErrorMassage = "No result found for query [select s from Student s where s.ID = :studentID]";
        Assertions.assertEquals(exceptedErrorMassage, actualErrorMassage);
    }

    @Test
    public void assignmentRepoGettingListOfCoursesByStudentId() {
        var testAssignment = new AssigmentCourse();
        testAssignment.setCourseID(5);
        testAssignment.setStudentID(TEST_ID);
        assignmentRepository.save(testAssignment);
        int exceptedLengthOfCourseList = courseRepository.findCoursesByStudentId(TEST_ID).size();
        int actualLengthOfCourseList = 1;
        assignmentRepository.delete(testAssignment);
        Assertions.assertEquals(exceptedLengthOfCourseList, actualLengthOfCourseList);
    }

    @Test
    public void studentServiceAddStudentToTheCourse() {
        var existingStudent = studentRepository.findByID(1);
        if (existingStudent.isPresent()) {
            int exceptedCourseQuantity = courseRepository.findCoursesByStudentId(existingStudent.get().getID()).size();
            int actualCourseQuantity = 3;
            Assertions.assertEquals(exceptedCourseQuantity, actualCourseQuantity);
        } else {
            throw new StudentNotFoundException("Student not found");
        }
    }

    @Test
    public void groupRepoCreateTestGroupWithZeroStudentQuantity() {
        int exceptedGroupsQuantity = groupRepository.findWithEqualOrLessStudents(1000).size();
        int actualGroupQuantity = dataGenerator.GROUPS.length;
        Assertions.assertEquals(exceptedGroupsQuantity, actualGroupQuantity);
    }
}
