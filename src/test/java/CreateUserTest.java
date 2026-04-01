import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.UserLoginModel;
import model.UserModel;
import org.junit.Before;
import org.junit.Test;
import steps.UserStep;

import static data.TestData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.UserStep.createUser;

// Тестирование создания пользователя
public class CreateUserTest extends BaseApiTest {
    UserModel userModel;
    UserLoginModel userLoginModel;
    UserStep userStep;

    // Создать уникальный email
    private String createUniqueLogin () {
        return EMAIL + System.currentTimeMillis();
    }

    @Before
    public void testCreateUser() {
        userModel = new UserModel(EMAIL, PASSWORD, NAME);
        userLoginModel = new UserLoginModel(EMAIL, PASSWORD);
        userStep = new UserStep();
    }

    @Test   // Создать пользователя
    @DisplayName("Создать пользователя. Позитивный тест")
    @Description("Пользователя можно создать при заполнении всех обязательных полей валидными данными")
    public void createUserTest() {
        String uniqueLogin = createUniqueLogin();

        UserModel userModel = new UserModel(uniqueLogin, PASSWORD, NAME);
        // Создать пользователя
        createUser(userModel)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");
    }

    @Test   // Создание двух одинаковых пользователей
    @DisplayName("Код ответа 403 Forbidden при создании двух одинаковых пользователей")
    @Description("При создании двух одинаковых пользователей вернется код ответа: 403 Forbidden")
    public void testUserAlreadyExists() {
        String uniqueLogin = createUniqueLogin();

        UserModel userModel = new UserModel(uniqueLogin, PASSWORD, NAME);
        // Создать пользователя первый раз
        createUser(userModel);

        // Отправить запрос на регистрацию с данными которые использовались первый раз
        createUser(userModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(MASSAGE_ALREADY_EXISTS));
    }

    @Test   // Не заполнен email при регистрации
    @DisplayName("Код ответа 403 Forbidden если не заполнено одно из обязательных полей - email")
    @Description("Если не заполнить email вернется код ответа 403 Forbidden")
    public void testNotFillEmail() {
        // Создать пользователя без email password
        UserModel userModel = new UserModel(null, PASSWORD, NAME);

        // Отправить запрос
        createUser(userModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(MASSAGE_REQUIRED_DATA_MISSING));
    }

    @Test   // Не заполнен password при регистрации
    @DisplayName("Код ответа 403 Forbidden если не заполнено одно из обязательных полей - password")
    @Description("Если не заполнить password вернется код ответа 403 Forbidden")
    public void testNotFillPassword() {
        String uniqueLogin = createUniqueLogin();
        // Создать пользователя без password
        UserModel userModel = new UserModel(uniqueLogin, null, NAME);

        // Отправить запрос
        createUser(userModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(MASSAGE_REQUIRED_DATA_MISSING));
    }

    @Test   // Не заполнен name при регистрации
    @DisplayName("Код ответа 403 Forbidden если не заполнено одно из обязательных полей - name")
    @Description("Если не заполнить name вернется код ответа 403 Forbidden")
    public void testNotFillName() {
        String uniqueLogin = createUniqueLogin();
        // Создать пользователя без name
        UserModel userModel = new UserModel(uniqueLogin, PASSWORD, null);
        // Отправить запрос
        createUser(userModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(MASSAGE_REQUIRED_DATA_MISSING));
    }
}
