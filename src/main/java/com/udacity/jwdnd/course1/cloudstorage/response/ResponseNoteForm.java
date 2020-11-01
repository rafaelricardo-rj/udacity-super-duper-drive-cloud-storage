package com.udacity.jwdnd.course1.cloudstorage.response;

import java.util.Map;

public class ResponseNoteForm {
    private boolean validated;
    private Map<String, String> errorMessages;
    private int noteId;

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
}
