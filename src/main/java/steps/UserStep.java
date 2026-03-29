package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UserLoginModel;
import model.UserModel;

import static data.TestData.*;
import static io.restassured.RestAssured.given;

// Шаги для пользователя
public class UserStep {

    @Step ("Создать пользователя")
    public static Response createUser(UserModel userModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(userModel)
                .when()
                .post(CREATE_USER)
                .then()
                .extract().response();
    }

    @Step ("Авторизироваться пользователем")
    public static Response authorizeUser(UserLoginModel userLoginModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(userLoginModel)
                .when()
                .post(LOGIN_USER)
                .then()
                .extract().response();
    }

    @Step ("Удалить пользователя")
    public static void deleteUser(String accessToken) {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .delete(DELETE_USER)
                .then()
                .extract().response();
    }



}
