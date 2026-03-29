package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;
import model.UserLoginModel;

import static data.TestData.CREATE_ORDERS;
import static io.restassured.RestAssured.given;

// Шаги для заказа
public class OrderSteps {

    @Step ("Создать заказ с авторизацией")
    public static Response createOrderWithAuthorization(UserLoginModel userLoginModel, OrderModel orderModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", userLoginModel)
                .body(orderModel)
                .when()
                .post(CREATE_ORDERS)
                .then()
                .extract().response();
    }

    @Step ("Создать заказ без авторизации")
    public static Response createOrderNotAuthorization(OrderModel orderModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post(CREATE_ORDERS)
                .then()
                .extract().response();
    }
}
