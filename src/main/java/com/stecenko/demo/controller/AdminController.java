package com.stecenko.demo.controller;

import com.stecenko.demo.model.Role;
import com.stecenko.demo.model.User;
import com.stecenko.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public String add(User user, Model model, String newRoles) {
        user.setRoles(magic(newRoles));
        userService.save(user);
        return "redirect:/admin/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        String[] rolz = new String[]{"USER", "ADMIN"};
        model.addAttribute("allRoles", rolz);
        return "add";
    }

    @GetMapping("/delete")
    public String delete(long id, Model model) {
        userService.deleteById(id);
        return "redirect:/admin/list";
    }

    @GetMapping("/list")
    public String allUser(Model model) {
        model.addAttribute("listz", userService.findAll());
        return "list";
    }

    @GetMapping(value = "/edit")
    public String edit(Model model, long id) {
        model.addAttribute("user", userService.findById(id));
        String[] rolz = new String[]{"USER", "ADMIN"};
        model.addAttribute("allRoles", rolz);
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(Model model, User user, String newRoles) {
        user.setRoles(magic(newRoles));
        userService.edit(user.getId(), user);
        return "redirect:/admin/list";
    }

    public Set<Role> magic(String roles) {
        Set<Role> answer = new HashSet<>();
        for (String s : roles.split(",")) {
            answer.add(new Role(s));
        }
        return answer;
    }
}
