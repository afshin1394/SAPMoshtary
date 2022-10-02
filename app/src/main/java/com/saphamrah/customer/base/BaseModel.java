package com.saphamrah.customer.base;

public interface BaseModel {

    void setLogToDB(
            Integer logType,
            String message,
            String logClass,
            String logActivity,
            String functionParent,
            String functionChild
    );

    void onDestroy();
}
