package org.ntnu.idi.idatt2105.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Class representing a user login in the application. */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class UserLogin {

    private String username;
    private String password;
    private String email;
}
