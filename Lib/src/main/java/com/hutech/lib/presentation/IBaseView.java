package com.hutech.lib.presentation;

public interface IBaseView<T extends IBasePresenter> {

    void setPresenter(T presenter);

    void onFailure(String errorMessage);

    void onTimeout();
}