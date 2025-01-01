package com.sr.mobile_backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Roles {

    SUPER_ADMIN
            (
                    Set.of(
                            Permissions.ADMIN_CREATE,
                            Permissions.ADMIN_DELETE,
                            Permissions.ADMIN_UPDATE,
                            Permissions.ADMIN_READ,
                            Permissions.SUPER_ADMIN_DELETE,
                            Permissions.SUPER_ADMIN_CREATE,
                            Permissions.SUPER_ADMIN_UPDATE,
                            Permissions.SUPER_ADMIN_READ
                    )
            )
    ,

    ADMIN(
            Set.of(
                    Permissions.ADMIN_CREATE,
                    Permissions.ADMIN_DELETE,
                    Permissions.ADMIN_UPDATE,
                    Permissions.ADMIN_READ
            )
    ),
    USER(Collections.emptySet());

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var auth=getPermissions()
                .stream()
                .map( permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        auth.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return auth;
    }

}
