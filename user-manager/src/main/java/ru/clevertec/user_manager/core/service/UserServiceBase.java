package ru.clevertec.user_manager.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import ru.clevertec.user_manager.api.dto.AppUserDto;
import ru.clevertec.user_manager.core.mapper.UserMapper;
import ru.clevertec.user_manager.core.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceBase implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public AppUserDto findByUsername(String username) {
        return userMapper.toAppUserDto(userRepository.findByUsername(username)
            .orElseThrow(() -> Problem.builder()
                .withTitle("user not found")
                .withStatus(Status.NOT_FOUND)
                .build()));
    }
}
