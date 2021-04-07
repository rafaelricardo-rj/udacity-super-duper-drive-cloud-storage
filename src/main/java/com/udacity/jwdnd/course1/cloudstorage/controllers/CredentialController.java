package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pojos.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.pojos.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.response.ResponseCredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.response.ResponseNoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;

    /**
     *
     * @param userService
     * @param credentialService
     */
    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    /**
     * Create a new credential.
     *
     * @param credentialForm
     * @param auth
     * @param result
     * @return ResponseCredentialForm
     */
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseCredentialForm credentialPost(@ModelAttribute @Valid CredentialForm credentialForm, Authentication auth, BindingResult result){
        int newCredential;
        Map<String, String> errors;
        ResponseCredentialForm respCredentialForm = new ResponseCredentialForm();
        if(result.hasErrors()){

            //System.out.println(result.getAllErrors());
            errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            respCredentialForm.setValidated(false);
            respCredentialForm.setErrorMessages(errors);
            return respCredentialForm;
        } else {
            // Check if credential is duplicated
            if(credentialService.isDuplicated(credentialForm.getUrl(), credentialForm.getUsername())){
                respCredentialForm.setValidated(false);
                errors = new HashMap<>();
                errors.put("error", "User already available.");
                respCredentialForm.setErrorMessages(errors);
                return respCredentialForm;
            }
            try {
                int userId = userService.getUser(auth.getName()).getUserid();
                newCredential = credentialService.addCredential(new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(), null, credentialForm.getPassword(), userId));
                if(newCredential > 0){
                    Credential credential = credentialService.getCredential(newCredential);
                    respCredentialForm.setValidated(true);
                    respCredentialForm.setCredentialId(newCredential);
                    respCredentialForm.setEncryptedPassword(credential.getPassword());
                }
            } catch (Exception e){
                respCredentialForm.setValidated(false);
                errors = new HashMap<>();
                errors.put("error", "Failed while saving the credential in the database."+e.getMessage());
                respCredentialForm.setErrorMessages(errors);
            }
        }
        return respCredentialForm;
    }

    /**
     * Delete a credential
     * @param id
     * @param auth
     * @return ResponseCredentialForm
     */
    @DeleteMapping(value = "{id}/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseCredentialForm credentialDelete(@PathVariable Integer id, Authentication auth){
        ResponseCredentialForm responseCredentialForm = new ResponseCredentialForm();
        if(id != null) {
            Credential credential = credentialService.getCredential(id);
            User user = userService.getUser(auth.getName());
            //only allow to delete the credential if you are the owner
            if(credential.getUserid() == user.getUserid()){
                int deleteCredential = credentialService.delete(credential.getCredentialid());
                if(deleteCredential > 0){
                    responseCredentialForm.setValidated(true);
                }
            }
        }
        return responseCredentialForm;
    }

    /**
     * Get a credential by Id
     * @param id
     * @param auth
     * @return ResponseCredentialForm
     */
    @PostMapping(value = "{id}/fetch", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseCredentialForm credentialFetch(@PathVariable Integer id, Authentication auth){
        ResponseCredentialForm responseCredentialForm = new ResponseCredentialForm();
        if(id != null) {
            Credential credential = credentialService.getCredential(id);
            User user = userService.getUser(auth.getName());
            //only allow to decrypt the credential if you are the owner
            if(credential.getUserid() == user.getUserid()){
                String decryptedPassword = credentialService.decryptPassword(credential.getPassword(), credential.getKey());
                responseCredentialForm.setValidated(true);
                responseCredentialForm.setCredentialId(credential.getCredentialid());
                responseCredentialForm.setDecryptedPassword(decryptedPassword);
            }
        }
        return responseCredentialForm;
    }

    /**
     * Update a credential selected by it Id
     * @param credentialForm
     * @param id
     * @param auth
     * @param result
     * @return ResponseCredentialForm
     */
    @PatchMapping(value = "{id}/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseCredentialForm noteUpdate(@ModelAttribute @Valid CredentialForm credentialForm, @PathVariable Integer id, Authentication auth, BindingResult result){
        Map<String, String> errors;
        ResponseCredentialForm responseCredentialForm = new ResponseCredentialForm();
        if(id != null) {
            Credential credential = credentialService.getCredential(id);
            User user = userService.getUser(auth.getName());
            //only allow to update the credential if you are the owner
            if(credential.getUserid() == user.getUserid()){
                if(result.hasErrors()){
                    //System.out.println(result.getAllErrors());
                    errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
                    responseCredentialForm.setValidated(false);
                    responseCredentialForm.setErrorMessages(errors);
                    return responseCredentialForm;
                } else {
                    try {
                        credential.setUrl(credentialForm.getUrl());
                        credential.setUsername(credentialForm.getUsername());
                        credential.setPassword(credentialForm.getPassword());
                        int update = credentialService.update(credential);
                        if (update > 0){
                            String encryptedPassword = credentialService.getCredential(id).getPassword();
                            responseCredentialForm.setValidated(true);
                            responseCredentialForm.setCredentialId(id);
                            responseCredentialForm.setEncryptedPassword(encryptedPassword);
                        }
                    } catch (Exception e){
                        responseCredentialForm.setValidated(false);
                        errors = new HashMap<>();
                        errors.put("error", "Failed while saving the credential in the database."+e.getMessage());
                        responseCredentialForm.setErrorMessages(errors);
                    }
                }
            }
        }
        return responseCredentialForm;
    }
}
