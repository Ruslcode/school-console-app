package com.foxminded.university.controller;

import com.foxminded.university.components.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
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
