package com.user.access.management.service.impl;

import com.user.access.management.dto.UserDto;
import com.user.access.management.entity.Role;
import com.user.access.management.entity.User;
import com.user.access.management.enums.RoleName;
import com.user.access.management.exceptions.ResourceAlreadyExist;
import com.user.access.management.exceptions.ResourceNotExist;
import com.user.access.management.repository.RoleRepository;
import com.user.access.management.repository.UserRepository;
import com.user.access.management.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {

        userExists(userDto.getEmail());

        User user = new User();
        BeanUtils.copyProperties(userDto, user, "password");

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName(RoleName.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);


        User saveUser = userRepository.save(user);

        log.info("save user successfully");

        UserDto returnUserDto = new UserDto();
        BeanUtils.copyProperties(saveUser, returnUserDto, "password");
        return returnUserDto;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new ResourceNotExist("User not found with id: " + id + "!"));

        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ADMIN))) {
            throw new ResourceAlreadyExist("Admin user can't be deleted!");
        }

        userRepository.delete(user);

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return Arrays.stream(userRepository.findAll().toArray()).map(user -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto, "password");
            return userDto;
        }).toList();
    }

    private void userExists(String email) {
        if (userRepository.findByEmail(email) != null && userRepository.findByEmail(email).getEmail().equals(email)) {
            throw new ResourceAlreadyExist("User already exists with email: " + email + "!");
        }
    }
}
