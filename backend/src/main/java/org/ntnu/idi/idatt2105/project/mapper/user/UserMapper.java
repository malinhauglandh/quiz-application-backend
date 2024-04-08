package org.ntnu.idi.idatt2105.project.mapper.user;

import org.ntnu.idi.idatt2105.project.dto.user.UserCreationDTO;
import org.ntnu.idi.idatt2105.project.dto.user.UserLoginDTO;
import org.ntnu.idi.idatt2105.project.entity.user.User;
import org.springframework.stereotype.Component;

/**
 * Mapper class for the User entity. Converts User entities to UserDTOs.
 * It is used to convert User entities to UserDTOs before sending the data to the
 * frontend.
 */
@Component
public class UserMapper {

    /**
     * Converts a User entity to a UserDTO.
     * @param user The User entity to convert.
     * @return The UserDTO.
     */
    public UserLoginDTO toDTO(User user) {
        return new UserLoginDTO(user.getUsername(), user.getPassword());
    }

    /**
     * Converts a UserDTO to a User entity.
     * @param userDTO The UserDTO to convert.
     * @return The User entity.
     */
    public User toUser(UserLoginDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    /**
     * Converts a User entity to a UserCreationDTO.
     * @param user The User entity to convert.
     * @return The UserCreationDTO.
     */
    public UserCreationDTO toUserCreationDTO(User user) {
        return new UserCreationDTO(user.getUsername(), user.getPassword(), user.getEmail());
    }

    /**
     * Converts a UserCreationDTO to a User entity.
     * @param userCreationDTO The UserCreationDTO to convert.
     * @return The User entity.
     */
    public User toNewUser(UserCreationDTO userCreationDTO) {
        return new User(
                userCreationDTO.getUsername(),
                userCreationDTO.getEmail(),
                userCreationDTO.getPassword());
    }
}
