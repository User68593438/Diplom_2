package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Создать пользователя
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private String email;
    private String password;
    private String name;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
