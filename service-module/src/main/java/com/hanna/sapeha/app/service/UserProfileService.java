package com.hanna.sapeha.app.service;

import com.hanna.sapeha.app.service.model.UserProfileDTO;

public interface UserProfileService {

    UserProfileDTO updateUserByParameters(UserProfileDTO userForm, String email);

    UserProfileDTO getAllInfoUser(String email);
}
