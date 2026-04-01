import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.UserLoginModel;
import model.UserModel;
import org.junit.Before;
import org.junit.Test;
import steps.UserStep;

import static data.TestData.*;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static steps.UserStep.authorizeUser;
import static steps.UserStep.createUser;

// Тестирование авторизации пользователя
public class UserLoginTest extends BaseApiTest{
    UserModel userModel;
    UserLoginModel userLoginModel;
    UserStep userStep;

    @Before
    public void testCreateUser() {
        userModel = new UserModel(EMAIL, PASSWORD, NAME);
        userLoginModel = new UserLoginModel(EMAIL, PASSWORD);
        userStep = new UserStep();

        createUser(userModel);
    }

    @Test   // Авторизировать пользователя
    @DisplayName("Авторизировать пользователя. Позитивный тест")
    @Description("Пользователя можно авторизировать при заполнении всех обязательных полей валидными данными")
    public void authorizationUserPositiveTest() {
        // Сохранить email
        String email = userModel.getEmail();
        // Сохранить name
        String name = userModel.getName();

        // Передать валидные значения email и password
        authorizeUser(userLoginModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));
    }

    @Test   // Не заполнен email при авторизации
    @DisplayName("Код ответа 401 Unauthorized если не заполнен email при авторизации")
    @Description("Если не заполнить email вернется код ответа 401 Unauthorized")
    public void testAuthorizationNotFillEmail() {
        // Авторизироваться без email
        UserLoginModel userLogin = new UserLoginModel(null, PASSWORD);

        // Отправить запрос
        authorizeUser(userLogin)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(MASSAGE_INCORRECT_REQUIRED_DATA));
    }

    @Test   // Не заполнен password при авторизации
    @DisplayName("Код ответа 401 Unauthorized если не заполнен password при авторизации")
    @Description("Если не заполнить password вернется код ответа 401 Unauthorized")
    public void testAuthorizationNotFillPassword() {
        // Авторизироваться без password
        UserLoginModel userLogin = new UserLoginModel(EMAIL, null);

        // Отправить запрос
        authorizeUser(userLogin)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(MASSAGE_INCORRECT_REQUIRED_DATA));
    }

    @Test   // Email при авторизации заполнен с ошибкой
    @DisplayName("Код ответа 401 Unauthorized если email при авторизации указан с ошибкой")
    @Description("Если email указан с ошибкой, вернется код ответа 401 Unauthorized")
    public void testAuthorizationErrorFillEmail() {
        // Заполнить значение email с ошибкой
        UserLoginModel userLogin = new UserLoginModel("error" + EMAIL, PASSWORD);

        // Отправить запрос
        authorizeUser(userLogin)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(MASSAGE_INCORRECT_REQUIRED_DATA));
    }

    @Test   // Password при авторизации заполнен с ошибкой
    @DisplayName("Код ответа 401 Unauthorized если password при авторизации указан с ошибкой")
    @Description("Если password указан с ошибкой, вернется код ответа 401 Unauthorized")
    public void testAuthorizationErrorFillPassword() {
        // Заполнить значение password с ошибкой
        UserLoginModel userLogin = new UserLoginModel(EMAIL, PASSWORD + "error");

        // Отправить запрос
        authorizeUser(userLogin)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(MASSAGE_INCORRECT_REQUIRED_DATA));
    }
}
