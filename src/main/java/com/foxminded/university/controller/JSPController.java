package com.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JSPController {
    @Value("${spring.application.name}")
    private String appName;
    private String loginName = "Vasya23455";
    private String accauntType = "Student";


    @GetMapping("/jsp")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("loginName",loginName);
        model.addAttribute("accountType",accauntType);
        return "homePage";
    }

}
