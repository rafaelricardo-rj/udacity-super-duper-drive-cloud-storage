package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pojos.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.pojos.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(UserService userService, NoteService noteService, CredentialService credentialService, FileService fileService){
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String homeView(NoteForm noteForm, CredentialForm credentialForm, Model model, Authentication auth){
        User user = userService.getUser(auth.getName());
        model.addAttribute("notes", this.noteService.getNotesByUserId(user.getUserid()));
        model.addAttribute("credentials", this.credentialService.getCredentials(user.getUserid()));
        model.addAttribute("files", this.fileService.getFiles(user.getUserid()));
        return "home";
    }
}
