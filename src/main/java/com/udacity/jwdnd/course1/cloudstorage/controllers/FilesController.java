package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.sun.jdi.request.ExceptionRequest;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FilesController {
    private UserService userService;
    private FileService fileService;

    /**
     * Constructor
     * @param userService
     * @param fileService
     */
    public FilesController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    /**
     * Upload a file
     * @param multipartFile
     * @param auth
     * @return ResponseFileUpload
     */
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseFileUpload fileNew(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication auth){
        List<String> errors = new ArrayList<>();
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
                    responseFileUpload.setValidated(true);
                    responseFileUpload.setFileId(insert);
                    responseFileUpload.setFilename(multipartFile.getOriginalFilename());
                    String url = "/file/download/".concat(multipartFile.getOriginalFilename());
                    responseFileUpload.setUrl(url);
                }
            } catch (Exception e){
                return responseFileUpload;
            }
        }
        return responseFileUpload;
    }

    /**
     * Delete a file
     * @param id
     * @param auth
     * @return ResponseFileUpload
     */
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

    /**
     * Download a file
     * @param request
     * @param response
     * @param fileName
     * @param auth
     * @throws IOException
     */
    @RequestMapping("/download/{fileName:.+}")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,  @PathVariable("fileName") String fileName, Authentication auth) throws IOException {
        User user = userService.getUser(auth.getName());
        File file = new File(fileService.getUploadDir() + File.separator + String.valueOf(user.getUserid()) + File.separator + fileName);
        if (file.exists()) {

            //get the mimetype
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }
}
