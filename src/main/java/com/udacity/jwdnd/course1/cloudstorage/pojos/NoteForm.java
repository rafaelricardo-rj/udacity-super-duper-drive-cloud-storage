package com.udacity.jwdnd.course1.cloudstorage.pojos;

import javax.validation.constraints.NotEmpty;

public class NoteForm {
    @NotEmpty(message = "Please enter title.")
    private String notetitle;
    @NotEmpty(message = "Please enter a description.")
    private String notedescription;
    private int userid;

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
