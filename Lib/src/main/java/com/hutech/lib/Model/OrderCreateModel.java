package com.hutech.lib.Model;

import java.io.Serializable;
import java.util.List;

public class OrderCreateModel
{
    private String checkinTime;

    private String tableOrderID;

    private List<ListOrder> listOrder;

    private String staffID;

    public String getCheckinTime ()
    {
        return checkinTime;
    }

    public void setCheckinTime (String checkinTime)
    {
        this.checkinTime = checkinTime;
    }

    public String getTableOrderID ()
    {
        return tableOrderID;
    }

    public void setTableOrderID (String tableOrderID)
    {
        this.tableOrderID = tableOrderID;
    }

    public List<ListOrder> getListOrder ()
    {
        return listOrder;
    }

    public void setListOrder (List<ListOrder> listOrder)
    {
        this.listOrder = listOrder;
    }

    public String getStaffID ()
    {
        return staffID;
    }

    public void setStaffID (String staffID)
    {
        this.staffID = staffID;
    }

    @Override
    public String toString()
    {
        return "[checkinTime = "+checkinTime+", tableOrderID = "+tableOrderID+", listOrder = "+listOrder+", staffID = "+staffID+"]";
    }
    public static class ListOrder
    {
        private String productID;
        private int amount;

        public int getAmount ()
        {
            return amount;
        }

        public void setAmount (int amount)
        {
            this.amount = amount;
        }

        public String getProductID ()
        {
            return productID;
        }

        public void setProductID (String productID)
        {
            this.productID = productID;
        }

        @Override
        public String toString()
        {
            return "[productID = "+productID+"]";
        }
    }


}
