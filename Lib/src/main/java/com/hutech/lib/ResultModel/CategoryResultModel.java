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
        private int id;

        private int productCount;

        private String categoryName;

        public int getId ()
        {
            return id;
        }

        public void setId (int id)
        {
            this.id = id;
        }

        public int getProductCount ()
        {
            return productCount;
        }

        public void setProductCount (int productCount)
        {
            this.productCount = productCount;
        }

        public String getCategoryName ()
        {
            return categoryName;
        }

        public void setCategoryName (String categoryName)
        {
            this.categoryName = categoryName;
        }

        @Override
        public String toString()
        {
            return "[id = "+id+", productCount = "+productCount+", categoryName = "+categoryName+"]";
        }
    }

}
