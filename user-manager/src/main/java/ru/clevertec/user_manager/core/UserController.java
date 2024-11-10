package ru.clevertec.user_manager.core;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.user_manager.api.UserApi;
import ru.clevertec.user_manager.api.dto.AppUserDto;
import ru.clevertec.user_manager.core.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public AppUserDto findUserByUsername(String username) {
        return userService.findByUsername(username);
    }
}
