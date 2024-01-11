package com.example.officebureauapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),
    RESERVATION_READ("reservation:read"),
    RESERVATION_UPDATE("reservation:update"),
    RESERVATION_CREATE("reservation:create"),
    RESERVATION_DELETE("reservation:delete"),
    DEPARTMENT_READ("department:read"),
    DEPARTMENT_UPDATE("department:update"),
    DEPARTMENT_CREATE("department:create"),
    DEPARTMENT_DELETE("department:delete"),

    ;

    @Getter
    private final String permission;
}
