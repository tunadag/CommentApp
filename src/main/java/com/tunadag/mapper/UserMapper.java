package com.tunadag.mapper;

import com.tunadag.dto.request.UserCreateRequestDto;
import com.tunadag.dto.response.UserCreateResponseDto;
import com.tunadag.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toUser(final UserCreateRequestDto dto);
    UserCreateResponseDto toUserCreateResponseDto(final User user);
    UserCreateResponseDto toUserCreateResponseDto(final UserCreateRequestDto dto);
}
