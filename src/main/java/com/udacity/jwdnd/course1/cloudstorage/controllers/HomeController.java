package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pojos.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private NoteService noteService;

    public HomeController(UserService userService, NoteService noteService){
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping
    public String homeView(NoteForm noteForm, Model model){
        User user = userService.getUser("rafaelricardo");
        model.addAttribute("notes", this.noteService.getNotesByUserId(user.getUserid()));
        //System.out.println(note.length);
        return "home";
    }
}
