package com.bhuvanesh.talenthive;


public interface OnSubmitClickListener<T> {
    void onSubmit(T object);
    void onSubmit(int type);
    void onSubmit();
}
