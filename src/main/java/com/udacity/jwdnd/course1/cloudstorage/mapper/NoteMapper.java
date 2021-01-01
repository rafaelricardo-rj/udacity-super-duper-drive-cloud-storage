package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface NoteMapper {


    @Select("SELECT * FROM NOTES WHERE userid = #{userid} ORDER BY noteid DESC")
    List<Note> getAllNotesByUserId(int userId);

    @Results(id = "noteResultMap", value = {
        @Result(property = "user", column = "userid", one = @One(select = "com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper.getUserById", fetchType= FetchType.LAZY))
    })
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNote(int noteId);

    @Insert("INSERT INTO NOTES ( notetitle, notedescription, userid) VALUES ( #{notetitle}, #{notedescription}, #{userid} )")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int deleteNote(int noteid);

    @Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription} WHERE noteid =#{noteid}")
    int updateNote(Note note);
}
