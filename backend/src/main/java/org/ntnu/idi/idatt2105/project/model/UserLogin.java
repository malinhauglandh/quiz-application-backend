package org.ntnu.idi.idatt2105.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Model class for user login. */
@Setter
@Getter
@NoArgsConstructor
public class UserLogin {

    private String username;
    private String password;

    @Override
    public String toString() {
        return "Username: " + username + "\n" + "Password: " + password;
    }
}
