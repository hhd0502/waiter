package com.hutech.lib.ResultModel;

import java.util.List;

import me.imstudio.core.ui.pager.Selectable;

public class TableResultModel {
    private List<Data> data;

    private String status;

    public List<Data> getData ()
    {
        return data;
    }

    public void setData (List<Data> data)
    {
        this.data = data;
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
        return "[data = "+data+", status = "+status+"]";
    }

    public static class Data extends Selectable {
        private String name;

        private String _id;

        private int status;

        public Data(String id, String name, int status){
            this.name = name;
            this.status = status;
            this._id = id;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String get_id ()
        {
            return _id;
        }

        public void set_id (String _id)
        {
            this._id = _id;
        }

        public int getStatus ()
        {
            return status;
        }

        public void setStatus (int status)
        {
            this.status = status;
        }

        @Override
        public String toString()
        {
            return "[name = "+name+", _id = "+_id+", status = "+status+"]";
        }
    }

}
