package ru.clevertec.newspaper.config.sequrity.user.dto;

import java.util.List;


public record AppUser(

    Long id,

    String username,

    String password,

    List<Role> roles

) {
}