package com.zikan.e_shop.service.user;

import com.zikan.e_shop.dto.UserDto;
import com.zikan.e_shop.exception.AlreadyExistsExcption;
import com.zikan.e_shop.exception.ResourceNotFoundExcepion;
import com.zikan.e_shop.model.User;
import com.zikan.e_shop.repository.UserRepository;
import com.zikan.e_shop.request.CreateUserRequest;
import com.zikan.e_shop.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
 public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User getUserById(Long userId) {
        return  userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundExcepion("User not found!"));

    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);

                }).orElseThrow(()-> new AlreadyExistsExcption("Oops!" + request.getEmail() + " already exist"));

    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());

           return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundExcepion("User not found"));
    }



    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () ->{
            throw  new ResourceNotFoundExcepion("User not found");
        });

    }

    //create helper mtd to convert user oBJECT TO dto
    @Override
    public UserDto convertUser (User user){
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
