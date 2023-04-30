package com.foxminded.university.controller;

import com.foxminded.university.components.AssignmentService;
import com.foxminded.university.components.StudentService;
import com.foxminded.university.repositories.AssignmentRepository;
import com.foxminded.university.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AssignmentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/addCourse")
    public String setAddStudentCourse() {
        return "addCourse";
    }

    @RequestMapping(value = "/assignmentCourse", method = RequestMethod.POST)
    public String addStudent(@RequestParam int studentID, @RequestParam int courseID) {
        studentService.addCourse(studentID,courseID);
        return "redirect:/";
    }
}
