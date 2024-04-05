package org.ntnu.idi.idatt2105.project.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginDTO {
    private String username;
    private String password;
}
