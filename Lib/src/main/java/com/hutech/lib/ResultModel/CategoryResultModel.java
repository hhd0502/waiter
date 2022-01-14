package com.hutech.lib.ResultModel;

import java.util.List;

public class CategoryResultModel {

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

    public class Data
    {
        private String id;

        private String categoryName;

        private String categoryDescription;

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getCategoryName ()
        {
            return categoryName;
        }

        public void setCategoryName (String categoryName)
        {
            this.categoryName = categoryName;
        }

        public String getCategoryDescription ()
        {
            return categoryDescription;
        }

        public void setCategoryDescription (String categoryDescription)
        {
            this.categoryDescription = categoryDescription;
        }

        @Override
        public String toString()
        {
            return "[id = "+id+", categoryName = "+categoryName+", categoryDescription = "+categoryDescription+"]";
        }
    }

}
