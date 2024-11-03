package com.zikan.e_shop.request;

import com.zikan.e_shop.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    List<Role> roles;
}
