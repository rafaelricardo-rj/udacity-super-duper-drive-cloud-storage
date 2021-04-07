package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredential(int credentialid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid} ORDER BY credentialid DESC")
    List<Credential> getAllCredentialsByUserId(int userId);

    @Insert("INSERT INTO CREDENTIALS ( url, username, key, password, userid) VALUES ( #{url}, #{username}, #{key}, #{password}, #{userid} )")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid", keyColumn = "credentialid")
    int insert(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int deleteCredential(int credentialid);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} WHERE credentialid =#{credentialid}")
    int updateCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE url=#{url} AND username=#{username} ")
    Credential getCredByUrlAndUsername(String url, String username);
}
