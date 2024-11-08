package ru.clevertec.user_manager.api.dto;

import java.util.List;


public record AppUserDto(

    Long id,

    String username,

    String password,

    List<RoleDto> roles

) {
}