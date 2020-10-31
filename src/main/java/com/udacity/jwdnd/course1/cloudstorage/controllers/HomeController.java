package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.pojos.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private UserService userService;

    public HomeController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public String homeView(Authentication auth, NoteForm noteForm){
        //User user = (User) auth.getPrincipal();
        String username = auth.getName();
        int userId = userService.getUser(auth.getName()).getUserid();
        System.out.println(userId);
        return "home";
    }
}
