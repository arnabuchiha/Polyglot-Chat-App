package com.vitualsenseltd.arnab.chatterx.language_save;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class lang {

    @SerializedName("language")
    @Expose
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
