package com.derpate.bankapp.model.security;

public enum Permission {
    USERS_PERM("users:permission"),
    ADMINS_PERM("admins:permission");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
