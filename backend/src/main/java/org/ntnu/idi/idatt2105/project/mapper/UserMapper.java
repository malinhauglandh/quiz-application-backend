package org.ntnu.idi.idatt2105.project.mapper;

import org.ntnu.idi.idatt2105.project.dto.user.UserCreationDTO;
import org.ntnu.idi.idatt2105.project.dto.user.UserLoginDTO;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserLoginDTO toDTO(User user) {
        return new UserLoginDTO(user.getUsername(), user.getPassword());
    }

    public User toUser(UserLoginDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public UserCreationDTO toUserCreationDTO(User user) {
        return new UserCreationDTO(user.getUsername(), user.getPassword(), user.getEmail());
    }

    public User toNewUser(UserCreationDTO userCreationDTO) {
        return new User(
                userCreationDTO.getUsername(),
                userCreationDTO.getEmail(),
                userCreationDTO.getPassword());
    }
}
