package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    Note getNote(int userid);

    @Insert("INSERT INTO NOTES ( notetitle, notedescription, userid) VALUES ( #{notetitle}, #{notedescription}, #{userid} )")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insert(Note note);
}
