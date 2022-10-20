package com.saphamrah.customer.base;

public class BasePermissionModel {
    private final String permission;
    private final boolean granted;

    public BasePermissionModel(String permission, boolean granted) {
        this.permission = permission;
        this.granted = granted;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isGranted() {
        return granted;
    }
}
