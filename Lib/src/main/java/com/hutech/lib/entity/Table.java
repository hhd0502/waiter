package com.hutech.lib.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import me.imstudio.core.ui.pager.Selectable;

public class Table extends Selectable implements Serializable {
    @SerializedName("_id")
    private String id;
    @SerializedName("status")
    private int status;
    @SerializedName("name")
    private String name;

    public void setId(String id){
      this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public void setStatus(int newStatus)
    {
        this.status = newStatus;
    }
}
