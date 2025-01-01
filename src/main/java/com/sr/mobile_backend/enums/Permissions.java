package com.sr.mobile_backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permissions {

    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    SUPER_ADMIN_CREATE("super:create"),
    SUPER_ADMIN_READ("super:read"),
    SUPER_ADMIN_UPDATE("super:update"),
    SUPER_ADMIN_DELETE("super:delete");

    private final String permission;

}
