package com.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {

    @GetMapping("/schedule")
    public String showSchedule(Model model) {
        String schedule = "schedule";
        model.addAttribute(schedule);
        return "schedule";
    }
}
