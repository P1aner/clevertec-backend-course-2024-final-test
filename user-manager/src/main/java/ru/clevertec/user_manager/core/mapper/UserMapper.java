package ru.clevertec.user_manager.core.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.user_manager.api.dto.AppUserDto;
import ru.clevertec.user_manager.core.model.AppUser;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {RoleMapper.class})
public interface UserMapper {

    AppUserDto toAppUserDto(AppUser appUser);

}
