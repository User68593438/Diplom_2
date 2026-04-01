package data;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.List;

public class TestData {
    // Базовый URL
    public static final String BASE_URL = "https://stellarburgers.education-services.ru";

    // Генерируем уникальные данные для пользователя
    static Faker user = new Faker();
    public static final String EMAIL = user.internet().emailAddress();
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String NAME = user.name().firstName();

    // Ингредиенты для бургера:
    // - bun = "61c0c5a71d1f82001bdaaa6d" - Флюоресцентная булка R2-D3
    // - filling = "61c0c5a71d1f82001bdaaa6f" - Мясо бессмертных моллюсков Protostomia

    // Создание списка ингредиентов
    public static final List<String> INGREDIENTS = Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"); // Валидный хеш ингредиентов
    public static final List<String> INGREDIENT_INVALID_HASH_BUN = Arrays.asList("invalid_hash_bun", "61c0c5a71d1f82001bdaaa6f"); // Невалидный хеш ингредиента булочка
    public static final List<String> INGREDIENT_INVALID_HASH_FILLING = Arrays.asList("61c0c5a71d1f82001bdaaa6d", "invalid_hash_filling"); // Невалидный хеш ингредиента начинка

    // Эндпоинты
    public static final String CREATE_USER = "/api/auth/register";
    public static final String LOGIN_USER = "/api/auth/login";
    public static final String DELETE_USER = "/api/auth/user";
    public static final String CREATE_ORDERS = "/api/orders";

    // Текст сообщения код ответа 403 Forbidden при регистрации
    public static final String MASSAGE_ALREADY_EXISTS = "User already exists"; //  Регистрация уже существующего пользователя
    public static final String MASSAGE_REQUIRED_DATA_MISSING = "Email, password and name are required fields"; // Не заполнено одно из обязательных полей
    // Текст сообщения код ответа 401 Unauthorized при авторизации
    public static final String MASSAGE_INCORRECT_REQUIRED_DATA = "email or password are incorrect"; // Логин и пароль неверные или нет одного из полей
    // Текст сообщения код ответа 400 Bad Request при создании заказа без ингредиентов
    public static final String MASSAGE_NO_INGREDIENTS = "Ingredient ids must be provided"; // Не передали ни один ингредиент

}
