package com.saphamrah.customer.base;

import java.lang.ref.WeakReference;

public class BasePresenter <TView extends BaseView,TModel extends BaseModel>{
    protected WeakReference<TView> view;
    protected TModel model;
    public BasePresenter(TView view) {
        this.view = new WeakReference<TView>(view);
    }

}
