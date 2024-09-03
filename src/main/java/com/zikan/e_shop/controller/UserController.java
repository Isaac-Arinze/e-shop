package com.zikan.e_shop.controller;


import com.zikan.e_shop.dto.UserDto;
import com.zikan.e_shop.exception.ResourceNotFoundExcepion;
import com.zikan.e_shop.model.User;
import com.zikan.e_shop.request.CreateUserRequest;
import com.zikan.e_shop.request.UserUpdateRequest;
import com.zikan.e_shop.response.APIResponse;
import com.zikan.e_shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.hibernate.grammars.hql.HqlParser.CONFLICT;
import static org.hibernate.grammars.hql.HqlParser.NOT_EQUAL;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<APIResponse> getUserById (@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUser(user);
            return ResponseEntity.ok(new APIResponse("User found successfully", userDto));
        }catch (ResourceNotFoundExcepion e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createUser (@RequestBody CreateUserRequest request){
        try {

        User user = userService.createUser(request);
        UserDto userDto = userService.convertUser(user);
        return ResponseEntity.ok(new APIResponse("User created successfully", userDto));

    }
        catch (ResourceNotFoundExcepion e){
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }
    @PutMapping
    public ResponseEntity<APIResponse> updateUser (@RequestBody UserUpdateRequest request, @PathVariable Long userId) {

        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUser(user);
            return ResponseEntity.ok(new APIResponse("User updated successfully", userDto));

        }catch (ResourceNotFoundExcepion e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("{userId}/delete")
    public ResponseEntity <APIResponse> deleteUser (@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok(new APIResponse("user successfully deleted", null));
        }
        catch (ResourceNotFoundExcepion e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));

        }
    }

}
