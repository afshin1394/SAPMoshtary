package com.saphamrah.customer.base;

public interface BasePresenterOps {

    void checkInsertLogToDB(
            Integer logType,
            String message,
            String logClass,
            String logActivity,
            String functionParent,
            String functionChild
    );

    void onDestroy();

}
