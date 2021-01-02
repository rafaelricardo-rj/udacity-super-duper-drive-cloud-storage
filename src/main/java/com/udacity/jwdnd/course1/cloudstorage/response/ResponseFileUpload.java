package com.udacity.jwdnd.course1.cloudstorage.response;

import java.util.Map;

public class ResponseFileUpload {
    private boolean validated;
    private Map<String, String> errorMessages;
    private int fileId;

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
