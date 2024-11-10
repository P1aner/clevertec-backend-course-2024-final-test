package ru.clevertec.user_manager.core.service;

import ru.clevertec.user_manager.api.dto.AppUserDto;

public interface UserService {
    AppUserDto findByUsername(String username);
}
