package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.jsonclass.UserChangeDataJson;
import ru.yandex.practicum.jsonclass.UserCreateJson;
import ru.yandex.practicum.jsonclass.UserLoginJson;
import static io.restassured.RestAssured.given;
import static ru.yandex.practicum.constants.Constants.*;

public class UserSteps {
    @Step("Создание уникального пользователя")
    public ValidatableResponse userCreate(UserCreateJson userCreateJson){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userCreateJson)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("Создание дублирующего пользователя")
    public ValidatableResponse userDuplicateCreate(UserCreateJson userCreateJson){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userCreateJson)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("Создание пользователя без email")
    public ValidatableResponse userCreateWithoutEmail(UserCreateJson userCreateJson){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userCreateJson)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("Создание пользователя без пароля")
    public ValidatableResponse userCreateWithoutPassword(UserCreateJson userCreateJson){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userCreateJson)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("Создание пользователя без имени")
    public ValidatableResponse userCreateWithoutName(UserCreateJson userCreateJson){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userCreateJson)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse userDelete(String token){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .delete(CHANGE_USER_DATA_OR_DELETE_OR_GET_DATA_PATH)
                .then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse userLogin(UserLoginJson userLoginJson){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userLoginJson)
                .when()
                .post(LOG_USER_PATH)
                .then();
    }

    @Step("Авторизация пользователя с неверным логином")//Ave Maria! Deus Vult!
    public ValidatableResponse userLoginWhitIncorrectLogin(UserLoginJson userLoginJson){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userLoginJson)
                .when()
                .post(LOG_USER_PATH)
                .then();
    }

    @Step("Авторизация пользователя с неверным паролем")//Ave Maria! Deus Vult!
    public ValidatableResponse userLoginWhitIncorrectPassword(UserLoginJson userLoginJson){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userLoginJson)
                .when()
                .post(LOG_USER_PATH)
                .then();
    }

    @Step("Изменение данных пользователя")
    public ValidatableResponse userUpDateData(UserChangeDataJson userChangeDataJson, String token){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(userChangeDataJson)
                .when()
                .patch(CHANGE_USER_DATA_OR_DELETE_OR_GET_DATA_PATH)
                .then();
    }

    @Step("Изменение данных пользователя без авторизации")
    public ValidatableResponse userUpDateDataWithoutLogin(UserChangeDataJson userChangeDataJson){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userChangeDataJson)
                .when()
                .patch(CHANGE_USER_DATA_OR_DELETE_OR_GET_DATA_PATH)
                .then();
    }

}
