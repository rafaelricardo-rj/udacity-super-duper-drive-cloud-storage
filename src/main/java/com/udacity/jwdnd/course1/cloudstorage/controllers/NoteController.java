package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pojos.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.response.ResponseNoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * This form submit was adapted from this site: https://shahediqbal.com/spring-mvc-and-jquery-ajax-form-submit-and-validation/
 * */

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;

    private UserService userService;

    public NoteController(UserService userService, NoteService noteService){
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseNoteForm notePost(@ModelAttribute @Valid NoteForm noteForm, Authentication auth, BindingResult result){
        int newNote;
        Map<String, String> errors;
        ResponseNoteForm responseNoteForm = new ResponseNoteForm();
        if(result.hasErrors()){

            //System.out.println(result.getAllErrors());
            errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            responseNoteForm.setValidated(false);
            responseNoteForm.setErrorMessages(errors);
            return responseNoteForm;
        } else {
            try {
                int userId = userService.getUser(auth.getName()).getUserid();
                newNote = noteService.addNote(new Note(null, noteForm.getNotetitle(), noteForm.getNotedescription(), userId));
                if(newNote > 0){
                    responseNoteForm.setValidated(true);
                    responseNoteForm.setNoteId(newNote);
                }
            } catch (Exception e){
                responseNoteForm.setValidated(false);
                errors = new HashMap<>();
                errors.put("error", "Failed while saving the note in the database.");
                responseNoteForm.setErrorMessages(errors);
            }
        }
        return responseNoteForm;
    }

    @DeleteMapping(value = "{id}/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseNoteForm noteDelete(@PathVariable Integer id, Authentication auth){
        ResponseNoteForm responseNoteForm = new ResponseNoteForm();
        if(id != null) {
            Note note = noteService.get(id);
            User user = userService.getUser(auth.getName());
            //only allow to delete the note if you are the owner
            if(note.getUserid() == user.getUserid()){
                int deleteNote = noteService.delete(note.getNoteid());
                if(deleteNote > 0){
                    responseNoteForm.setValidated(true);
                }
            }
        }
        return responseNoteForm;
    }

    @PatchMapping(value = "{id}/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseNoteForm noteUpdate(@ModelAttribute @Valid NoteForm noteForm, @PathVariable Integer id, Authentication auth, BindingResult result){
        Map<String, String> errors;
        ResponseNoteForm responseNoteForm = new ResponseNoteForm();
        if(id != null) {
            Note note = noteService.get(id);
            User user = userService.getUser(auth.getName());
            //only allow to update the note if you are the owner
            if(note.getUserid() == user.getUserid()){
                if(result.hasErrors()){
                    //System.out.println(result.getAllErrors());
                    errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
                    responseNoteForm.setValidated(false);
                    responseNoteForm.setErrorMessages(errors);
                    return responseNoteForm;
                } else {
                    try {
                        note.setNotetitle(noteForm.getNotetitle());
                        note.setNotedescription(noteForm.getNotedescription());
                        int update = noteService.update(note);
                        if (update > 0){
                            responseNoteForm.setValidated(true);
                            responseNoteForm.setNoteId(id);
                        }
                    } catch (Exception e){
                        responseNoteForm.setValidated(false);
                        errors = new HashMap<>();
                        errors.put("error", "Failed while saving the note in the database.");
                        responseNoteForm.setErrorMessages(errors);
                        //System.out.println(result);
                    }
                }
            }
        }
        return responseNoteForm;
    }
}
