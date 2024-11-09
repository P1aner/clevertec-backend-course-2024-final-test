package ru.clevertec.newspaper.config.sequrity.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.newspaper.config.sequrity.user.dto.AppUser;

@FeignClient("users")
public interface UserClient {

    @GetMapping(value = "/users", consumes = "application/json")
    AppUser getUserByUsername(@RequestParam
                              String username);
}
