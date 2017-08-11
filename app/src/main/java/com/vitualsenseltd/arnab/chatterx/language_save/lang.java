package com.vitualsenseltd.arnab.chatterx.language_save;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class lang {

    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("password")
    @Expose
    private String password;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
