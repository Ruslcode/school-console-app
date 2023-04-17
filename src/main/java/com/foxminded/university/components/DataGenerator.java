package com.foxminded.university.components;

import com.foxminded.university.exceptions.ExceptionConstants;
import com.foxminded.university.models.Student;
import com.foxminded.university.utils.CoursesArrayBuilder;
import com.foxminded.university.utils.GroupNameGenerator;
import com.foxminded.university.utils.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerator {
    private int studentLimit = 200;
    private NameGenerator studentNames = new NameGenerator(studentLimit);
    private GroupNameGenerator groupNames = new GroupNameGenerator();
    private final String[] STUDENTS = studentNames.getNames();
    public final String[] GROUPS = groupNames.getGroupNames();
    private final String[] COURSES = new CoursesArrayBuilder().getCourses();
    private static String INSERTING_INTO_STUDENTS = "INSERT INTO students(student_id, first_name, last_name) values (?,?,?)";
    private static String INSERTING_INTO_STUDENTS_GROUPS = "INSERT INTO university_groups(group_id, group_name) " + "VALUES(?,?)";
    private static String INSERTING_INTO_COURSES = "INSERT INTO courses(course_id, course_name, course_description) " +
            "VALUES(?,?,?)";
    private static String INSERTING_INTO_STUDENT_ASSIGNMENTATION = "INSERT INTO course_assignment(assignment_id ,student_id, course_id, group_id )" +
            "VALUES(?,?,?,?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void generate() {
        try {
            insertAssignmentationCourses();
            insertingDataIntoStudents();
            insertIntoStudentsGroups();
            insertIntoCourses();
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.SQL_INSERT_ERROR);
        }
    }

    private void insertingDataIntoStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        for (int i = 0, studentID = 1; i < STUDENTS.length; i++) {
            Student tempStudent = new Student();
            int randomStudentIndex = (int) (Math.random() * STUDENTS.length);
            String studentName = STUDENTS[randomStudentIndex].split("/")[0];
            String studentLastname = STUDENTS[randomStudentIndex].split("/")[1];
            tempStudent.setID(studentID);
            tempStudent.setFirstName(studentName);
            tempStudent.setLastName(studentLastname);
            students.add(tempStudent);
            studentID++;
        }

        jdbcTemplate.batchUpdate(INSERTING_INTO_STUDENTS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Student student = students.get(i);
                ps.setInt(1, student.getID());
                ps.setString(2, student.getFirstName());
                ps.setString(3, student.getLastName());
            }

            @Override
            public int getBatchSize() {
                return students.size();
            }
        });
    }

    private void insertIntoStudentsGroups() throws SQLException {
        int index = 0;
        for (String groupName : GROUPS) {
            jdbcTemplate.update(INSERTING_INTO_STUDENTS_GROUPS, index, groupName);
            index++;
        }

    }

    private void insertIntoCourses() throws SQLException {
        int index = 0;
        String someDescription = "some course description";
        for (String courseName : COURSES) {
            jdbcTemplate.update(INSERTING_INTO_COURSES, index, courseName, someDescription);
            index++;
        }

    }

    private void insertAssignmentationCourses() throws SQLException {

        int coursePigmentationLimit = 600;
        int courseRandomId;
        int groupRandomId = 0;
        int studentCoursePigmentation = 0;
        int studentCoursePigmentationLimit = 3;
        int assignmentID = 0;

        for (int i = coursePigmentationLimit, studentId = 0;
             i != 0 && studentId <= STUDENTS.length; ) {

            courseRandomId = (int) (Math.random() * COURSES.length);

            jdbcTemplate.update(INSERTING_INTO_STUDENT_ASSIGNMENTATION,
                    assignmentID, studentId, courseRandomId, groupRandomId);

            assignmentID++;
            studentCoursePigmentation++;

            if (studentCoursePigmentation == studentCoursePigmentationLimit) {
                groupRandomId = (int) (Math.random() * GROUPS.length);
                studentCoursePigmentation = 0;
                studentId++;
                i--;
            }
        }
    }
}

