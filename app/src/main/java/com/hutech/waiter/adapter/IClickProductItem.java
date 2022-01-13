package com.hutech.waiter.adapter;

import com.hutech.lib.ResultModel.ProductsResultModel;

public interface IClickProductItem {
    void onClick(ProductsResultModel.Data product, int position);
}
