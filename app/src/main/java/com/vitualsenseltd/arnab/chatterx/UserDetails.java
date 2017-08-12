package com.vitualsenseltd.arnab.chatterx;

public class UserDetails {
    public static String username = "";
    static String password = "";
    static String language= "";
    static String chatWith = "";
    public static long score=0;
    public String getLanguage(){
        return language;
    }
    public void setLanguage(String language){
        this.language=language;
    }
}
