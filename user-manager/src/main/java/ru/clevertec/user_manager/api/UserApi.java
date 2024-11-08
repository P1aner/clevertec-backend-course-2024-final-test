package ru.clevertec.user_manager.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.user_manager.api.dto.AppUserDto;

@RestController
@RequestMapping("api/users")
public interface UserApi {

    @GetMapping
    AppUserDto findUserByUsername(String username);

}
