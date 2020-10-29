package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.pojos.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.userdetails.User;
//import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    @GetMapping
    public String homeView(NoteForm noteForm){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername(); //get logged in username
        System.out.println(name);
        // second option for tests also not working
        //User userModel = userService.getUser("rafaelricardo");
        //System.out.println(userModel.getUsername());
        return "home";
    }
}
