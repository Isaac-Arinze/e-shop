package com.zikan.e_shop.data;


import com.zikan.e_shop.model.Role;
import com.zikan.e_shop.model.User;
import com.zikan.e_shop.repository.RoleRepository;
import com.zikan.e_shop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();

    }

    private void createDefaultUserIfNotExists() {

        Role userRole = roleRepository.findByName("ROLE_USER").get();



        for (int i = 1; i < 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;

            }

            User user = new User();
            user.setFirstName("Isaac");
            user.setLastName("Sunday" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default user " + i + " created successfully.");

        }
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();


        for (int i = 1; i < 5; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;

            }

            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Sunday" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin user " + i + " created successfully.");

        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles){

        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);

    }
}