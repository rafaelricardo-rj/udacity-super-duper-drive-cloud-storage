package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.FileUpload;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.response.ResponseCredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.response.ResponseFileUpload;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FilesController {
    private UserService userService;
    private FileService fileService;

    public FilesController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseFileUpload fileNew(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication auth){
        Map<String, String> errors = new HashMap<>();
        ResponseFileUpload responseFileUpload = new ResponseFileUpload();
        //validate file upload
        errors = fileService.validateFile(multipartFile);

        if(errors.size() > 0) {
            responseFileUpload.setValidated(false);
            responseFileUpload.setErrorMessages(errors);
            return responseFileUpload;
        } else {
            int userId = userService.getUser(auth.getName()).getUserid();
            FileUpload file = new FileUpload(
                    null,
                    multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    String.valueOf(multipartFile.getSize()),
                    userId
            );
            try {
                int insert = fileService.addFile(file);
                if(insert > 0){
                    fileService.uploadFile(multipartFile, userId);
                }
            } catch (Exception e){
                return responseFileUpload;
            }
        }
        return responseFileUpload;
    }

    @DeleteMapping(value = "{id}/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseFileUpload fileDelete(@PathVariable Integer id, Authentication auth){
        ResponseFileUpload responseFileUpload = new ResponseFileUpload();
        if(id != null) {
            FileUpload fileStored = fileService.getFile(id);
            User user = userService.getUser(auth.getName());
            //only allow to delete the credential if you are the owner
            if(fileStored.getUserid() == user.getUserid()){
                int deleteFile = fileService.delete(fileStored.getFileId());
                if(deleteFile > 0){
                    fileService.deleteFileFromStorage(fileStored.getFilename(), user.getUserid());
                    responseFileUpload.setValidated(true);
                }
            }
        }
        return responseFileUpload;
    }
}
