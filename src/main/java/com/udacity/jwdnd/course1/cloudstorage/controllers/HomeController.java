package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.pojos.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    @GetMapping
    public String homeView(Authentication auth, NoteForm noteForm){
        //User user = (User) auth.getPrincipal();
        String username = auth.getName();
        int userId = userService.getUser(auth.getName()).getUserid();

        return "home";
    }
}
