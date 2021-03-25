package com.example.drainage;

import android.app.Application;

public class Global extends Application {
    public String Email;
    public int flag, phone;
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


}
