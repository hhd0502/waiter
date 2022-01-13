package com.hutech.lib.Model;

import java.io.Serializable;

public class UserLoginModel implements Serializable {

    private String UserName;

    private String PassWord;

    public String getUserName ()
    {
        return UserName;
    }

    public void setUserName (String UserName)
    {
        this.UserName = UserName;
    }

    public String getPassWord ()
    {
        return PassWord;
    }

    public void setPassWord (String PassWord)
    {
        this.PassWord = PassWord;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [UserName = "+UserName+", PassWord = "+PassWord+"]";
    }
}
