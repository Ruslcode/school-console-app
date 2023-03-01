package com.foxmined.DBManipulation;

import com.foxmined.Utils.ExceptionConstants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecutor {

    private StringBuilder queryResult = new StringBuilder();
    private final int firstElementIndex = 0;
    private static final String Q_SEARCHING_STUDENTS_BY_COURSE = "\n" + "select distinct course_name, concat(first_name, ' ', last_name) as full_name \n" + "from students_courses_groups \n" + "left join students on students.student_id = students_courses_groups.student_id\n" + "left join courses on students_courses_groups.course_id = courses.course_id\n" + "where course_name Like '%s';";
    private static final String Q_SELECT_COURSES_BY_STUDENT_ID = "select  courses.course_id, course_name from students_courses_groups \n" + "left join courses on students_courses_groups.course_id = courses.course_id\n" + "where student_id = %s ;\n";
    private static final String Q_ADDING_STUDENT = "INSERT INTO students(student_id ,first_name, last_name) " + "VALUES (%s, '%s', '%s')";
    private static final String Q_DELETING_STUDENT = "delete from students where student_id = %s";
    private static final String Q_ADDING_STUDENT_TO_COURSE = "INSERT INTO students_courses_groups(student_id ,course_id, group_id) " + "VALUES (%s,'%s','%s')";
    private static final String Q_FINDING_GROUPS_WITH_LESS_SECULAR_STUDENTS_NUMBER = "select group_name, students_in_groups   \n" + "from students_groups \n" + "join (select distinct  group_id , (round(count(student_id)/3 )) as students_in_groups  \n" + "from students_courses_groups \n" + "group by group_id\n" + "order by group_id) t on t.group_id = students_groups.group_id\n" + "where t.students_in_groups <= %s";
    private static final String Q_DELETE_STUDENT_FROM_COURSE = "delete from students_courses_groups" + " where course_id = '%s'";
    private static final Connection CONNECTION;

    static {
        try {
            CONNECTION = ConnectionFactory.getInstance().getConnection();
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.NO_CONNECTION);
        }
    }

    public void findingAllStudentsByCourseName(String courseName) {

        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet rs = statement.executeQuery(String.format(Q_SEARCHING_STUDENTS_BY_COURSE, courseName));
            String course;
            String fullStudentName;

            while (rs.next()) {
                course = rs.getString("course_name");
                fullStudentName = rs.getString("full_name") + "\n";
                queryResult.append(course).append(" ").append(fullStudentName);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.QUERY_INVALID);
        }

        System.out.println(queryResult);
        queryResult.delete(firstElementIndex, queryResult.length());

    }

    public void selectingToDeleteCourses(String studentID) {
        try {
            StringBuilder selectResult = new StringBuilder();
            Statement statement = CONNECTION.createStatement();
            ResultSet rs = statement.executeQuery(String.format(Q_SELECT_COURSES_BY_STUDENT_ID, studentID));

            while (rs.next()) {
                selectResult.append(rs.getString("course_id")).append(" ").append(rs.getString("course_name"));
                selectResult.append("\n");
            }
            System.out.println("\nChose the course id to remove: \n" + selectResult);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCourse(String courseId) {
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(String.format(Q_DELETE_STUDENT_FROM_COURSE, courseId));
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.QUERY_INVALID);
        }
        System.out.println("Successfully deleted!");
    }


    public void createNewStudent(String[] studentInfo) {
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(String.format(Q_ADDING_STUDENT, studentInfo[0], studentInfo[1], studentInfo[2]));
            System.out.println("Student successfully added!");
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.QUERY_INVALID);
        }
    }

    public void deletingStudentsByCoursesId(String studentIndex) {
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(String.format(Q_DELETING_STUDENT, studentIndex));
            System.out.println("student deleted ;)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addingCourseToStudent(String[] courseStudentInformation) {
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(String.format(Q_ADDING_STUDENT_TO_COURSE, courseStudentInformation[0], courseStudentInformation[1], courseStudentInformation[2]));
            System.out.println("inserting completed ;)");

        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.QUERY_INVALID);
        }
    }

    public void findingAllGroupsLessEqualStudents(int numberStudents) {
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet rs = statement.executeQuery(String.format(Q_FINDING_GROUPS_WITH_LESS_SECULAR_STUDENTS_NUMBER, numberStudents));
            while (rs.next()) {
                queryResult.append(rs.getString("group_name"));
                queryResult.append(":");
                queryResult.append(rs.getString("students_in_groups"));
                queryResult.append("\n");
            }
            System.out.println(queryResult);
            queryResult.delete(firstElementIndex, queryResult.length());
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.QUERY_INVALID);
        }
    }
}
