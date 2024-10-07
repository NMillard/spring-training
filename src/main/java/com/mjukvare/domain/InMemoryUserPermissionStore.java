package com.mjukvare.domain;

import com.mjukvare.controllers.requests.UserType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
class InMemoryUserPermissionStore implements UserPermissionStore {
    private final Map<UserType, List<String>> permissions = Map.of(
            UserType.FREE, List.of(""),
            UserType.PREMIUM, List.of("users.view", "users.edit", "users.delete", "user.create")
    );
    
    @Override
    public List<String> getUserTypePermissions(UserType userType) {
        return permissions.get(userType);
    }
}
