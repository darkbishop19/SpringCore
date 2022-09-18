package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.web.request.UserRequest;
import com.edu.ulab.app.web.request.UserUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userRequestToUserDto(UserRequest userRequest);
    UserDto userUpdateRequestToUserDto ( UserUpdateRequest userUpdateRequest );
    UserRequest userDtoToUserRequest(UserDto userDto);

    UserUpdateRequest userDtoToUserUpdateRequest ( UserDto userDto);
}
