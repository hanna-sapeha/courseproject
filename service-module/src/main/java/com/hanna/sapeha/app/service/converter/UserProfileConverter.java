package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.repository.model.UserDetails;
import com.hanna.sapeha.app.service.model.UserProfileDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserProfileConverter {

    public UserProfileDTO convert(User user) {
        UserProfileDTO userProfile = new UserProfileDTO();
        userProfile.setId(user.getId());
        userProfile.setFirstname(user.getFirstname());
        userProfile.setLastname(user.getLastname());
        userProfile.setEmail(user.getEmail());

        UserDetails userDetails = user.getUserDetails();
        if (Objects.nonNull(userDetails)) {
            userProfile.setAddress(userDetails.getAddress());
            userProfile.setTelephone(userDetails.getTelephone());
        }
        return userProfile;
    }
}
