package com.stecenko.demo.controller;

import com.stecenko.demo.model.User;
import com.stecenko.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
  private   UserService userService;


    @GetMapping(value = "/demo")
    public String demo(ModelMap model) {
        userService.demo();
        model.addAttribute("listz", userService.findAll());
        return "list";
    }

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        return "login";
    }


    @GetMapping(value = "/user")
    public String getInfoAboutUser(ModelMap model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "infoAboutUser";
    }
}