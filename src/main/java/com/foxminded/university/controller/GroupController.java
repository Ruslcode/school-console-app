package com.foxminded.university.controller;

import com.foxminded.university.models.Group;
import com.foxminded.university.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GroupController {
    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/groups")
    public String showGroups(Model model) {
        List<Group> groups = groupRepository.findWithEqualOrLessStudents(100000);
        model.addAttribute("groups", groups);
        return "groups";
    }
}
