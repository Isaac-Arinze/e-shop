package com.zikan.e_shop.service.user;

import com.zikan.e_shop.dto.UserDto;
import com.zikan.e_shop.model.User;
import com.zikan.e_shop.request.CreateUserRequest;
import com.zikan.e_shop.request.UserUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();
//    User registerUser (RegistrationRequest registrationRequest);

    User getUserById (Long userId);
    User createUser (CreateUserRequest request);

    User findByEmail (String email);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser (Long userId);

    //create helper mtd to convert user oBJECT TO dto
    UserDto convertUser(User user);

    User getAuthenticationUser();
}
