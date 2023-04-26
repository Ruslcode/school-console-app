package com.foxminded.university.controller;

import com.foxminded.university.models.Student;
import com.foxminded.university.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    List<Student> classmates;

    @GetMapping("/addStudent")
    public String addStudent() {
        return "addStudent";
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String addStudent(@RequestParam String firstName, @RequestParam String lastName) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        studentRepository.save(student);
        return "redirect:/";
    }

    @GetMapping("/students")
    public String showStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/classmates")
    public String getStudent(@RequestParam("index") int id, Model model) {
        List<Student> classmates = studentRepository.findClassMates(id);
        model.addAttribute("classmates", classmates);
        return "classmates";
    }
}
