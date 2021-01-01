package com.udacity.jwdnd.course1.cloudstorage.pojos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CredentialForm {
    @NotNull
    @Size(max = 100, min = 10, message = "The URL must have between 10 and 100 characters.")
    @NotEmpty(message = "Please type an URL.")
    private String url;

    @NotNull
    @Size(max = 30, min = 3, message = "Username must have between 3 and 30 characters.")
    @NotEmpty(message = "Please enter an username.")
    private String username;

    @NotNull
    @Size(max = 30, min = 3, message = "Username must have between 0 and 200 characters.")
    @NotEmpty(message = "Please enter a password.")
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
