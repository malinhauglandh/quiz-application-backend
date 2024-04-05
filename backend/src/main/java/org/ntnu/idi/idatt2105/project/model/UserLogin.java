package org.ntnu.idi.idatt2105.project.model;

import lombok.*;

/** Class representing a user login in the application. */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLogin {

    private String username;
    private String password;
    private String email;
}
