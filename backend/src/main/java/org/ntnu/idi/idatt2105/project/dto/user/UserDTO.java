package org.ntnu.idi.idatt2105.project.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    /** The username of the user. */
    private String username;

    /** The id of the user. */
    private String id;

    /** The email of the user. */
    private String email;
}
