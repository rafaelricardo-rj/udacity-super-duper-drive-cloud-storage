package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileUpload;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    FileUpload getFile(int credentialid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    FileUpload getFileByFilename(String filename);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} ORDER BY fileId DESC")
    List<FileUpload> getAllFilesByUserId(int userId);

    @Insert("INSERT INTO FILES ( filename, contenttype, filesize, userid) VALUES ( #{filename}, #{contenttype}, #{filesize}, #{userid} )")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(FileUpload fileUpload);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(int fileId);

    /*@Update("UPDATE FILES SET filename=#{filename}, contenttype=#{contenttype}, filesize=#{filesize} WHERE fileId =#{fileId}")
    int updateFile(File file);*/
}
