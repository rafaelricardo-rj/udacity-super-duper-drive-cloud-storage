package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note){
        int insert = noteMapper.insert(note);

        return note.getNoteid();
    }

    public List<Note> getNotesByUserId(int userId){
        return noteMapper.getAllNotesByUserId(userId);
    }

    public Note get(int noteId){
        return noteMapper.getNote(noteId);
    }

    public Note getNoteByTitle(String title){
        return noteMapper.getNoteByTitle(title);
    }

    public Note getNoteByDesc(String desc){
        return noteMapper.getNoteByDescription(desc);
    }

    public int delete(int noteId){
        return noteMapper.deleteNote(noteId);
    }

    public int update(Note note){
        return noteMapper.updateNote(note);
    }

    public Boolean titleExist(String title){
        try {
            Note note = getNoteByTitle(title);
            if(note != null){
                return true;
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    public Boolean descriptionExist(String desc){
        try {
            Note note = getNoteByDesc(desc);
            if(note != null){
                return true;
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }
}
