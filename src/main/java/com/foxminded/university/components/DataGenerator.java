package com.foxminded.university.components;

import com.foxminded.university.exceptions.GroupNotFoundException;
import com.foxminded.university.models.AssigmentCourse;
import com.foxminded.university.models.Course;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import com.foxminded.university.repositories.AssignmentRepository;
import com.foxminded.university.repositories.CourseRepository;
import com.foxminded.university.repositories.GroupRepository;
import com.foxminded.university.repositories.StudentRepository;
import com.foxminded.university.utils.CoursesArrayBuilder;
import com.foxminded.university.utils.GroupNameGenerator;
import com.foxminded.university.utils.NameGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;


    public void insertDataIntoTables() {
        insertingDataIntoStudents();
        insertAssignmentationCourses();
        insertIntoCourses();
        insertIntoGroups();
        assignGroupToStudent();
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
            tempStudent.setGroupName("");
            students.add(tempStudent);
        }
        studentRepository.saveAll(students);

    }


    private void insertIntoGroups() {
        List<Group> groups = new ArrayList<>();
        for (String groupName : GROUPS) {
            Group tempGroup = new Group();
            tempGroup.setGroupName(groupName);
            groups.add(tempGroup);
        }
        groupRepository.saveAll(groups);
    }

    private void insertIntoCourses() {
        List<Course> courses = new ArrayList<>();
        String someDescription = "some course description";
        for (String courseName : COURSES) {
            Course tempCourse = new Course();
            tempCourse.setCourseDescription(someDescription);
            tempCourse.setCourseName(courseName);
            courses.add(tempCourse);
        }
        courseRepository.saveAll(courses);

    }

    private void insertAssignmentationCourses() {
        List<Student> students = studentRepository.findAll();
        List<AssigmentCourse> courseAssignments = new ArrayList<>();
        System.out.println(students.size());
        int coursePigmentationLimit = 600;
        int groupRandomId = 1;
        int studentCoursePigmentation = 0;
        int studentCoursePigmentationLimit = 3;
        int studentsIndexLimit = 199;
        int courseRandomId = 0;

        for (int i = coursePigmentationLimit, listIndex = 0;
             i != 0 && listIndex <= studentsIndexLimit; ) {
            AssigmentCourse tempAssignment = new AssigmentCourse();
            int studentID = students.get(listIndex).getID();


            courseRandomId++;
            if (courseRandomId == COURSES.length) {
                courseRandomId = 0;
            }
            tempAssignment.setCourseID(courseRandomId);
            tempAssignment.setGroupID(groupRandomId);
            tempAssignment.setStudentID(studentID);
            courseAssignments.add(tempAssignment);

            studentCoursePigmentation++;

            if (studentCoursePigmentation == studentCoursePigmentationLimit) {
                groupRandomId = (int) (Math.random() * GROUPS.length)+1;
                studentCoursePigmentation = 0;
                listIndex++;
                i--;
            }
        }
        assignmentRepository.saveAll(courseAssignments);
    }

    private void assignGroupToStudent() {
        List<Student> students = studentRepository.findAll();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            int studentID = student.getID();
            var group = groupRepository.findGroupByStudentID(studentID);
            if (group.isPresent()) {
                Group studentGroup = group.get();
                student.setGroupName(studentGroup.getGroupName());
                studentRepository.save(student);

            } else if (!group.isPresent()) {
                throw new GroupNotFoundException("GroupNotFound!");
            }
        }
    }
}

