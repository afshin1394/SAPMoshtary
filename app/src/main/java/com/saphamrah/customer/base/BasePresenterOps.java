package com.saphamrah.customer.base;

import java.lang.ref.WeakReference;

public interface BasePresenterOps<TView extends BaseView,TModel extends BaseModel> {

    abstract void  checkInsertLogToDB(
            Integer logType,
            String message,
            String logClass,
            String logActivity,
            String functionParent,
            String functionChild
    );

    abstract void onDestroy();

}
