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

    public Note getNoteByTitleAndDesc(String title, String desc){
        return noteMapper.getNoteByTitleAndDescription(title, desc);
    }

    public int delete(int noteId){
        return noteMapper.deleteNote(noteId);
    }

    public int update(Note note){
        return noteMapper.updateNote(note);
    }

    public Boolean isDuplicated(String title, String desc){
        try {
            Note note = getNoteByTitleAndDesc(title, desc);
            if(note != null){
                return true;
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }
}
