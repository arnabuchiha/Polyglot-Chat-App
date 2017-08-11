package com.vitualsenseltd.arnab.chatterx.Translation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class authPOJO {
    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
