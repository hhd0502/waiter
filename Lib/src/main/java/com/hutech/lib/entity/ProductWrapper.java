package com.hutech.lib.entity;

import com.google.gson.annotations.SerializedName;
import com.hutech.lib.ResultModel.ProductsResultModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductWrapper implements Serializable
{
    @SerializedName("docs")
    private List<ProductsResultModel.Data> products;

    public ProductWrapper()
    {
        products = new ArrayList<>();
    }
    public List<ProductsResultModel.Data> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsResultModel.Data> products)
    {
        this.products = products;
    }

    public void addProduct(ProductsResultModel.Data product)
    {
        if (isExisted(product) == -1)
        {
            product.setQuantity(1);
            products.add(product);
        }
        else {
            product.setQuantity(product.getQuantity()+1);
        }
    }

    private int isExisted(ProductsResultModel.Data product)
    {
        for(ProductsResultModel.Data i : products)
        {
            if (i.getId().equals(product.getId()))
                return products.indexOf(i);
        }
        return -1;
    }

    public int getQuantity()
    {
        return products.size();
    }
    public int getQuantity(int id)
    {
        return products.size();
    }
    public ProductsResultModel.Data getProductById(String id)
    {
        for (ProductsResultModel.Data i : products)
        {
            if (i.getId().equals(id))
                return i;
        }
        return null;
    }
}
