package ru.clevertec.user_manager.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.user_manager.core.model.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}