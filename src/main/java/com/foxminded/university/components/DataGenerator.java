package com.foxminded.university.components;

import com.foxminded.university.models.Student;
import com.foxminded.university.repositories.StudentRepository;
import com.foxminded.university.utils.CoursesArrayBuilder;
import com.foxminded.university.utils.GroupNameGenerator;
import com.foxminded.university.utils.NameGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerator {
    private static String INSERTING_INTO_STUDENTS = "INSERT INTO students(student_id, first_name, last_name) values (?,?,?)";
    private static String INSERTING_INTO_STUDENTS_GROUPS = "INSERT INTO university_groups(group_id, group_name, student_number) " + "VALUES(?,?,?)";
    private static String INSERTING_INTO_COURSES = "INSERT INTO courses(course_id, course_name, course_description) " +
            "VALUES(?,?,?)";
    private static String INSERTING_INTO_STUDENT_ASSIGNMENTATION = "INSERT INTO course_assignment(assignment_id ,student_id, course_id, group_id )" +
            "VALUES(?,?,?,?)";
    private final int studentLimit = 200;
    private NameGenerator studentNames = new NameGenerator(studentLimit);
    private GroupNameGenerator groupNames = new GroupNameGenerator();
    private final String[] STUDENTS = studentNames.getNames();
    public final String[] GROUPS = groupNames.getGroupNames();
    private final String[] COURSES = new CoursesArrayBuilder().getCourses();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TablesRewriter rewriter;
    
    @PostConstruct
    public void insertDataIntoTables() {
        rewriter.createTablespace();
        insertingDataIntoStudents();
        insertIntoGroups();
        insertIntoCourses();
        insertAssignmentationCourses();
    }

    public void insertingDataIntoStudents() {
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < STUDENTS.length; i++) {
            Student tempStudent = new Student();
            int randomStudentIndex = (int) (Math.random() * STUDENTS.length);
            String studentName = STUDENTS[randomStudentIndex].split("/")[0];
            String studentLastname = STUDENTS[randomStudentIndex].split("/")[1];

            tempStudent.setFirstName(studentName);
            tempStudent.setLastName(studentLastname);
            students.add(tempStudent);
        }
        studentRepository.saveAll(students);
    }


    private void insertIntoGroups() {
        int index = 0;
        for (String groupName : GROUPS) {
            jdbcTemplate.update(INSERTING_INTO_STUDENTS_GROUPS, index, groupName, 0);
            index++;
        }

    }

    private void insertIntoCourses() {
        int index = 0;
        String someDescription = "some course description";
        for (String courseName : COURSES) {
            jdbcTemplate.update(INSERTING_INTO_COURSES, index, courseName, someDescription);
            index++;
        }

    }

    private void insertAssignmentationCourses() {
        List<Student> students = studentRepository.findAll();
        System.out.println(students.size());

        int coursePigmentationLimit = 600;
        int groupRandomId = 0;
        int studentCoursePigmentation = 0;
        int studentCoursePigmentationLimit = 3;
        int assignmentID = 0;
        int studentsIndexLimit = 199;
        int courseRandomId = 0;

        for (int i = coursePigmentationLimit, listIndex = 0;
             i != 0 &&  listIndex <= studentsIndexLimit;) {
            int studentID = students.get(listIndex).getID();


            courseRandomId++;
            if (courseRandomId == COURSES.length){
                courseRandomId=0;
            }

            jdbcTemplate.update(INSERTING_INTO_STUDENT_ASSIGNMENTATION,
                    assignmentID, studentID, courseRandomId, groupRandomId);

            assignmentID++;
            studentCoursePigmentation++;

            if (studentCoursePigmentation == studentCoursePigmentationLimit) {
                groupRandomId = (int) (Math.random() * GROUPS.length);
                studentCoursePigmentation = 0;
                listIndex++;
                i--;
            }
        }
    }
}

