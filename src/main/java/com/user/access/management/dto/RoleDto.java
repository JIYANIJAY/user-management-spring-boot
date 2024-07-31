package com.user.access.management.dto;

import com.user.access.management.entity.User;
import com.user.access.management.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long id;
    private RoleName name;
    private List<User> users;
}
