package com.hutech.lib.ResultModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserLoginResultModel implements Serializable {
    private Data data;

    private String message;

    private String status;

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", message = "+message+", status = "+status+"]";
    }
    public class Data implements Serializable
    {
        private String avatar_url;

        private String phone_number;

        private String _id;

        private String fullname;

        private String email;

        private String username;

        private String token;

        public String getAvatarUrl ()
        {
            return avatar_url;
        }

        public void setAvatar_url (String avatar_url)
        {
            this.avatar_url = avatar_url;
        }

        public String getPhone_number ()
        {
            return phone_number;
        }

        public void setPhone_number (String phone_number)
        {
            this.phone_number = phone_number;
        }

        public String get_id ()
        {
            return _id;
        }

        public void set_id (String _id)
        {
            this._id = _id;
        }

        public String getFullname ()
        {
            return fullname;
        }

        public void setFullname (String fullname)
        {
            this.fullname = fullname;
        }

        public String getEmail ()
        {
            return email;
        }

        public void setEmail (String email)
        {
            this.email = email;
        }

        public String getUsername ()
        {
            return username;
        }

        public void setUsername (String username)
        {
            this.username = username;
        }

        public String getToken ()
        {
            return token;
        }

        public void setToken (String token)
        {
            this.token = token;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [avatar_url = "+avatar_url+", phone_number = "+phone_number+", _id = "+_id+", fullname = "+fullname+", email = "+email+", username = "+username+", token = "+token+"]";
        }
    }
}
