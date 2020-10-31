package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.sun.jdi.request.ExceptionRequest;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pojos.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.response.ResponseNoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * This form submit was adapted from this site: https://shahediqbal.com/spring-mvc-and-jquery-ajax-form-submit-and-validation/
 * */

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private UserService userService;

    public NoteController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseNoteForm notePost(@ModelAttribute @Valid NoteForm noteForm, Authentication auth, BindingResult result){
        int newNote;
        Map<String, String> errors;
        ResponseNoteForm responseNoteForm = new ResponseNoteForm();
        System.out.println(noteForm.getNotetitle());
        if(result.hasErrors()){

            //System.out.println(result.getAllErrors());
            errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            responseNoteForm.setValidated(false);
            responseNoteForm.setErrorMessages(errors);
            return responseNoteForm;
        } else {
            try {
                int userId = userService.getUser(auth.getName()).getUserid();
                newNote = noteMapper.insert(new Note(null, noteForm.getNotetitle(), noteForm.getNotedescription(), userId));
                if(newNote > 0){
                    responseNoteForm.setValidated(true);
                }
            } catch (Exception e){
                responseNoteForm.setValidated(false);
                errors = new HashMap<>();
                errors.put("error", "Failed while saving the note in the database.");
                responseNoteForm.setErrorMessages(errors);
                //System.out.println(result);
            }
        }
        return responseNoteForm;
    }
}
