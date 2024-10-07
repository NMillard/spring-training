package com.mjukvare.domain;

import com.mjukvare.controllers.requests.UserType;

import java.util.List;

public interface UserPermissionStore {
    List<String> getUserTypePermissions(UserType userType);
}
