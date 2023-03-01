package com.foxmined.DBManipulation;

import com.foxmined.Utils.CoursesArrayBuilder;
import com.foxmined.Utils.ExceptionConstants;
import com.foxmined.Utils.GroupNameGenerator;
import com.foxmined.Utils.StudentNameGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataGenerator {
    private final String[] STUDENTS = StudentNameGenerator.getNames();
    private final String[] GROUPS = GroupNameGenerator.getGroupNames();
    private final String[] COURSES = new CoursesArrayBuilder().getCourses();
    private static final Connection connection;

    static {
        try {
            connection = ConnectionFactory.getInstance().getConnection();
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.NO_CONNECTION);
        }
    }

    public void insertData() {
        try {
            insertIntoStudentCoursesGroups();
            insertingDataIntoStudents();
            insertIntoStudentsGroups();
            insertIntoCourses();
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.SQL_INSERT_ERROR);
        }
    }

    private void insertingDataIntoStudents() throws SQLException {
        int randomStudentIndex;

        PreparedStatement preset = connection.prepareStatement("INSERT INTO students(student_id, first_name, last_name) values (?,?,?)");
        String studentName;
        String studentLastname;

        for (int i = 0, studentID = 1; i < STUDENTS.length; i++) {

            randomStudentIndex = (int) (Math.random() * STUDENTS.length);
            studentName = STUDENTS[randomStudentIndex].split("/")[0];
            studentLastname = STUDENTS[randomStudentIndex].split("/")[1];
            preset.setInt(1, studentID);
            preset.setString(2, studentName);
            preset.setString(3, studentLastname);

            preset.addBatch();
            studentID++;
        }
        preset.executeLargeBatch();
    }

    private void insertIntoStudentsGroups() throws SQLException {
        int index = 0;
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students_groups(group_id, group_name) " +
                "VALUES(?,?)");

        for (String groupName : GROUPS) {
            preparedStatement.setInt(1, index);
            preparedStatement.setString(2, groupName);
            preparedStatement.addBatch();
            index++;
        }
        preparedStatement.executeBatch();
    }

    private void insertIntoCourses() throws SQLException {
        int index = 0;
        String someDescription = "some course description";
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO courses(course_id, course_name, course_description) " +
                "VALUES(?,?,?)");

        for (String courseName : COURSES) {
            preparedStatement.setInt(1, index);
            preparedStatement.setString(2, courseName);
            preparedStatement.setString(3, someDescription);
            preparedStatement.addBatch();
            index++;
        }
        preparedStatement.executeBatch();
    }

    private void insertIntoStudentCoursesGroups() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students_courses_groups(student_id, course_id, group_id )" +
                "VALUES(?,?,?)");

        int coursePigmentationLimit = 600;
        int courseRandomId;
        int groupRandomId;
        int studentCoursePigmentation = 0;
        int studentCoursePigmentationLimit = 3;

        for (int i = coursePigmentationLimit, studentId = 0;
             i != 0 && studentId <= STUDENTS.length; ) {

            groupRandomId = (int) (Math.random() * GROUPS.length);

            courseRandomId = (int) (Math.random() * COURSES.length);

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseRandomId);
            preparedStatement.setInt(3, groupRandomId);
            preparedStatement.addBatch();

            studentCoursePigmentation++;

            if (studentCoursePigmentation == studentCoursePigmentationLimit) {
                studentCoursePigmentation = 0;
                studentId++;
                i--;
            }
        }
        preparedStatement.executeBatch();
    }
}
