package model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// Авторизировать зарегистрированного пользователя
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginModel {

    private String email;
    private String password;

}
