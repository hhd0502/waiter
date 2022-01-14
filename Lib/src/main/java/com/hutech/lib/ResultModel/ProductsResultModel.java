package com.hutech.lib.ResultModel;

import java.io.Serializable;
import java.util.List;

public class ProductsResultModel {

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

    public class Data implements Serializable
    {
        private String image;

        private String decription;

        private int price;

        private String name;

        private String id;

        private String categoryId;

        public String getImage ()
        {
            return image;
        }

        public void setImage (String image)
        {
            this.image = image;
        }

        public String getDescription ()
        {
            return decription;
        }

        public void setDescription (String description)
        {
            this.decription = description;
        }

        public int getPrice ()
        {
            return price;
        }

        public void setPrice (int price)
        {
            this.price = price;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getCategoryId ()
        {
            return categoryId;
        }

        public void setCategoryId (String categoryId)
        {
            this.categoryId = categoryId;
        }

        @Override
        public String toString()
        {
            return "[image = "+image+", description = "+decription+", price = "+price+", name = "+name+", id = "+id+", categoryId = "+categoryId+"]";
        }
    }
}
