package ru.clevertec.user_manager.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.user_manager.core.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}