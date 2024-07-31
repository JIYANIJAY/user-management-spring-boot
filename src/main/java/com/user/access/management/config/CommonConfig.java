package com.user.access.management.config;

import com.user.access.management.entity.Role;
import com.user.access.management.entity.User;
import com.user.access.management.enums.RoleName;
import com.user.access.management.repository.RoleRepository;
import com.user.access.management.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Priority;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class CommonConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createRoleOnInit() {
        final Role user = roleRepository.findByName(RoleName.USER);
        if (user == null) {
            Role role = new Role();
            role.setName(RoleName.USER);
            roleRepository.save(role);
        }

        final Role admin = roleRepository.findByName(RoleName.ADMIN);
        if (admin == null) {
            Role role = new Role();
            role.setName(RoleName.ADMIN);
            roleRepository.save(role);
        }
    }

    @PostConstruct
    public void createUseOnInit() {

        userRepository.deleteAll();

        User user = new User();
        user.setFirstName("Temp");
        user.setLastName("Temp");
        user.setEmail("temp@temp.com");
        user.setPassword(passwordEncoder.encode("test"));

        Role role = roleRepository.findByName(RoleName.ADMIN);

        user.setRoles(Set.of(role));

        userRepository.save(user);
    }


}
