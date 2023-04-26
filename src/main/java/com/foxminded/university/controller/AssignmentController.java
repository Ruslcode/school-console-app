package com.foxminded.university.controller;

import com.foxminded.university.models.AssigmentCourse;
import com.foxminded.university.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AssignmentController {
    @Autowired
    private AssignmentRepository assignmentRepository;

    @GetMapping("/addCourse")
    public String setAddStudentCourse() {
        return "addCourse";
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String addStudent(@RequestParam int studentID, @RequestParam int groupID, @RequestParam int courseID) {
        AssigmentCourse assigmentCourse = new AssigmentCourse();
        assigmentCourse.setStudentID(studentID);
        assigmentCourse.setGroupID(groupID);
        assigmentCourse.setCourseID(courseID);
        assignmentRepository.save(assigmentCourse);
        return "redirect:/";
    }
}
