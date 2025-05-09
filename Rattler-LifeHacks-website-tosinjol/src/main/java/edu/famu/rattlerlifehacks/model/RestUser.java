package edu.famu.rattlerlifehacks.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestUser extends AUser{

    public RestUser(String username, String password) {
            this.userId = username;
            this.password = password;
    }

}
