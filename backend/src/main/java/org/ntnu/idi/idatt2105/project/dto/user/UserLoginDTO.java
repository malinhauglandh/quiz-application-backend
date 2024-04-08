package org.ntnu.idi.idatt2105.project.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginDTO {

    /** The username of the user. */
    private String username;

    /** The password of the user. */
    private String password;
}
