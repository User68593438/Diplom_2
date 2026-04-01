import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.OrderModel;
import model.UserLoginModel;
import model.UserModel;
import org.junit.Before;
import org.junit.Test;
import steps.UserStep;

import static data.TestData.*;
import static data.TestData.EMAIL;
import static data.TestData.PASSWORD;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.OrderSteps.createOrderNotAuthorization;
import static steps.OrderSteps.createOrderWithAuthorization;
import static steps.UserStep.createUser;

// Тестирование создания заказа
public class CreateOrderTest extends BaseApiTest{

    UserModel userModel;
    UserLoginModel userLoginModel;
    UserStep userStep;

    @Before
    public void testCreateUser() {
        userModel = new UserModel(EMAIL, PASSWORD, NAME);
        userLoginModel = new UserLoginModel(EMAIL, PASSWORD);
        userStep = new  UserStep();

        // Создать пользователя
        createUser(userModel);
    }

    @Test   // Создать заказ с авторизацией пользователя с ингредиентами. Позитивный тест
    @DisplayName("Создать заказ авторизированным пользователем с ингредиентами")
    @Description("Авторизированный пользователь может создать заказ с ингредиентами")
    public void createOrderAuthorizedUserWithIngredientsTest() {
        // Создать заказ со списком ингредиентов
        OrderModel orderModel = new OrderModel(INGREDIENTS);

        // Отправить запрос на создание заказа со списком ингредиентов авторизированным пользователем
        createOrderWithAuthorization(userLoginModel, orderModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test   // Создать заказ без авторизации пользователя с ингредиентами. Позитивный тест
    @DisplayName("Создать заказ неавторизированным пользователем с ингредиентами")
    @Description("Неавторизированный пользователь может создать заказ с ингредиентами")
    public void createOrderNoAuthorizedUserWithIngredientsTest() {
        // Создать заказ со списком ингредиентов
        OrderModel orderModel = new OrderModel(INGREDIENTS);

        // Отправить запрос на создание заказа со списком ингредиентов не авторизированным пользователем
        createOrderNotAuthorization(orderModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test   // Создать заказ с авторизацией и без ингредиентов
    @DisplayName("Код ответа 400 Bad Request если создать заказ авторизированным пользователем без ингредиентов")
    @Description("Если не передать ни один ингредиент вернется код ответа 400 Bad Request")
    public void createOrderAuthorizedUserNoIngredientsTest() {
        // Создать заказ без ингредиентов
        OrderModel orderModel = new OrderModel(null);

        // Отправить запрос на создание заказа авторизированным пользователем без ингредиентов
        createOrderWithAuthorization(userLoginModel, orderModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(MASSAGE_NO_INGREDIENTS));
    }

    @Test   // Создать заказ без авторизации и без ингредиентов
    @DisplayName("Код ответа 400 Bad Request если создать заказ без авторизации и без ингредиентов")
    @Description("Если не передать ни один ингредиент и не авторизироваться вернется код ответа 400 Bad Request")
    public void createOrderNoAuthorizedUserNoIngredientsTest() {
        // Создать заказ без ингредиентов
        OrderModel orderModel = new OrderModel(null);

        // Отправить запрос на создание заказа без ингредиентов неавторизированным пользователем
        createOrderNotAuthorization(orderModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(MASSAGE_NO_INGREDIENTS));
    }

    @Test // Создать заказ с авторизацией и неверным хешем ингредиента bun
    @DisplayName("Код ответа 500 Internal Server Error если создать заказ авторизированным пользователем и неверным хешем ингредиента bun")
    @Description("Если авторизироваться и передать неверный хеш ингредиента bun,  вернется код ответа 500 Internal Server Error")
    public void createOrderAuthorizedUserIncorrectHashIngredientBun() {
        // Создать заказ с неверным хешем ингредиента bun
        OrderModel orderModel = new OrderModel(INGREDIENT_INVALID_HASH_BUN);

        // Отправить запрос на создание заказа авторизированным пользователем с неверным хешем ингредиента bun
        createOrderWithAuthorization(userLoginModel, orderModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Test // Создать заказ без авторизации и с неверным хешем ингредиента filling
    @DisplayName("Код ответа 500 Internal Server Error если создать заказ без авторизации пользователя и с неверным хешем ингредиент filling")
    @Description("Если не авторизироваться и передать неверный хеш ингредиента filling, вернется код ответа 500 Internal Server Error")
    public void createOrderNoAuthorizedUserIncorrectHashIngredientFilling() {
        // Создать заказ с неверным хешем ингредиента filling
        OrderModel orderModel = new OrderModel(INGREDIENT_INVALID_HASH_FILLING);

        // Отправить запрос на создание заказа с неверным хешем ингредиента filling и неавторизированным пользователем
        createOrderNotAuthorization(orderModel)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }
}
