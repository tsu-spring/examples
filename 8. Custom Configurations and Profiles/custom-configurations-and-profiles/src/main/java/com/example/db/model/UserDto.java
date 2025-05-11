package com.example.db.model;

import com.example.db.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserDto {

    private String username;

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
