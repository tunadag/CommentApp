package com.tunadag.dto.response;

import com.tunadag.repository.entity.EUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateResponseDto {

    private String name;
    private String surName;
    private String email;
    private EUserType userType;

}
