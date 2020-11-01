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

    public int delete(int noteId){
        return noteMapper.deleteNote(noteId);
    }
}
