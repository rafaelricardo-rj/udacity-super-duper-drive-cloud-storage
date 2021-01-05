package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.FileStorageException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.FileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    private final FileMapper fileMapper;
    public static final long MAX_FILE_SIZE_IN_BYTES = 12582912; // 12 MB

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public FileUpload getFile(int fileId){
        return fileMapper.getFile(fileId);
    }

    public List<FileUpload> getFiles(int userId){
        return fileMapper.getAllFilesByUserId(userId);
    }

    public int addFile(FileUpload fileUpload){
        int result = fileMapper.insert(fileUpload);
        return fileUpload.getFileId();
    }

    public int delete(int fileId){
        return fileMapper.deleteFile(fileId);
    }

    public List validateFile(MultipartFile fileUpload){
        List<String> errors = new ArrayList<>();
        if(fileUpload.isEmpty()){
            errors.add("There is no file");
        }
        if(fileUpload.getSize() > MAX_FILE_SIZE_IN_BYTES){
            errors.add("The file size is bigger then permitted");
        }
        FileUpload fileStored = fileMapper.getFileByFilename(fileUpload.getOriginalFilename());
        if(fileStored != null){
            errors.add("There is a file with the same name already. Choose another name.");
        }
        return errors;
    }

    public void uploadFile(MultipartFile file, int userId) {
        String userFolder = uploadDir + File.separator + String.valueOf(userId);
        if(!new File(userFolder).exists()){
            new File(userFolder).mkdir();
        }
        try {
            Path copyLocation = Paths.get(userFolder + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }

    public void deleteFileFromStorage(String filename, int userId){
        String userFolder = uploadDir + File.separator + String.valueOf(userId) + File.separator + filename;
        if(new File(userFolder).exists()){
            new File(userFolder).delete();
        }
    }

    public String getUploadDir(){
        return this.uploadDir;
    }
}
