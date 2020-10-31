package com.udacity.jwdnd.course1.cloudstorage.pojos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NoteForm {

    @NotNull
    @Size(max = 20, min = 3, message = "Title must have between 3 and 20 characters.")
    @NotEmpty(message = "Please enter title.")
    private String notetitle;

    @NotNull
    @Size(max = 1000, min = 3, message = "Description must have between 3 and 1000 characters.")
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
