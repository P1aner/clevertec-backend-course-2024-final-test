package ru.clevertec.newspaper.config.sequrity.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.newspaper.config.sequrity.user.dto.AppUser;

@FeignClient("users")
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users", consumes = "application/json")
    AppUser getUserByUsername(@RequestParam String username);
}
