package com.foxmined.Utils;

import com.foxmined.DBManipulation.QueryExecutor;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class ConsoleMenu {
    private Scanner scanner = new Scanner(System.in);

    private final QueryExecutor queryExecutor = new QueryExecutor();

    public void run() throws SQLException {
        consoleAppMenu();
    }

    private void consoleAppMenu() {

        int option = -1;
        int findAllGroupOption = 1;
        int findStudentByCourseNameOption = 2;
        int createNewStudentOption = 3;
        int deleteStudentOption = 4;
        int addStudentToCourseOption = 5;
        int removeStudentFromCourseOption = 6;
        int exitOption = 0;

        while (option != 0) {

            System.out.println("Please select an option:");
            System.out.println("1. Find all groups with less or equal students' number");
            System.out.println("2. Find all students related to the course with the given name");
            System.out.println("3. Add a new student");
            System.out.println("4. Delete a student by the STUDENT_ID");
            System.out.println("5. Add a student to the course (from a list)");
            System.out.println("6. Remove the student from one of their courses");
            System.out.println("0. Exit");
            option = scanner.nextInt();
            if (option == findAllGroupOption) {
                System.out.println("Type number of students to find groups with less or equal students number: ");
                scanner = new Scanner(System.in);
                queryExecutor.findingAllGroupsLessEqualStudents(scanner.nextInt());
            } else if (option == findStudentByCourseNameOption) {
                System.out.println("Chose the course name: " + Arrays.toString(new CoursesArrayBuilder().getCourses()) + ".\nType course name:");
                scanner = new Scanner(System.in);
                queryExecutor.findingAllStudentsByCourseName(scanner.nextLine());
            } else if (option == createNewStudentOption) {
                System.out.println("Paste information abb stud (like: index_first name_last name): ");
                scanner = new Scanner(System.in);
                String[] studentInfo = scanner.nextLine().split("_");
                queryExecutor.createNewStudent(studentInfo);
            } else if (option == deleteStudentOption) {
                System.out.println("Paste the student index to delete: ");
                scanner = new Scanner(System.in);
                queryExecutor.deletingStudentsByCoursesId(scanner.nextLine());
            } else if (option == addStudentToCourseOption) {
                System.out.println("Paste student id, course id, group id (regex _ !): ");
                scanner = new Scanner(System.in);
                String[] studentInformation = scanner.nextLine().split("_");
                queryExecutor.addingCourseToStudent(studentInformation);
            } else if (option == removeStudentFromCourseOption) {
                System.out.println("Type student id: ");
                scanner = new Scanner(System.in);
                queryExecutor.selectingToDeleteCourses(scanner.nextLine());
                scanner = new Scanner(System.in);
                queryExecutor.deleteCourse(scanner.nextLine());
            } else if (option == exitOption) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option, please try again.");
            }
        }
    }
}

