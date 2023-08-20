package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.dto.OrderDTO;
import static io.restassured.RestAssured.given;
import static ru.yandex.practicum.constants.Constants.*;

public class OrderSteps {

    @Step("Создание заказа с авторизацией")
    public ValidatableResponse orderCreate(OrderDTO orderDTO, String token){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(orderDTO)
                .when()
                .post(CREATE_ORDERS_OR_GET_USER_LIST_ORDERS_PATH)
                .then();
    }

    @Step("Создание заказа без авторизацией")
    public ValidatableResponse orderCreateWithoutLogin(OrderDTO orderDTO){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(orderDTO)
                .when()
                .post(CREATE_ORDERS_OR_GET_USER_LIST_ORDERS_PATH)
                .then();
    }

    @Step("Создание заказа без ингредиентов")
    public ValidatableResponse orderCreateWithOutIngredients(OrderDTO orderDTO, String token){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(orderDTO)
                .when()
                .post(CREATE_ORDERS_OR_GET_USER_LIST_ORDERS_PATH)
                .then();
    }

    @Step("Создание заказа с неверным хешем ингредиентов")
    public ValidatableResponse orderCreateWithIncorrectHash(OrderDTO orderDTO){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(orderDTO)
                .when()
                .post(CREATE_ORDERS_OR_GET_USER_LIST_ORDERS_PATH)
                .then();
    }

    @Step("Получение списка заказов авторизованного пользователя")
    public ValidatableResponse orderUserGet(String token) {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get(CREATE_ORDERS_OR_GET_USER_LIST_ORDERS_PATH)
                .then();
    }

    @Step("Получение списка заказов не авторизованного пользователя")
    public ValidatableResponse orderUserGetWithoutLogin() {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .when()
                .get(CREATE_ORDERS_OR_GET_USER_LIST_ORDERS_PATH)
                .then();
    }

}
