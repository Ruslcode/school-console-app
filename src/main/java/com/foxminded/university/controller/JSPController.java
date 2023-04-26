package com.foxminded.university.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JSPController {
    @Value("${spring.application.name}")
    private String appName;
    @Value("${application.username}")
    private String loginName;
    @Value("${application.accountType}")
    private String accauntType;

    @GetMapping("/jsp")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("loginName", loginName);
        model.addAttribute("accountType", accauntType);
        return "welcome";
    }

}
