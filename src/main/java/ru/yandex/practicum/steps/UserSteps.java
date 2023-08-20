package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.dto.UserChangeDataDTO;
import ru.yandex.practicum.dto.UserCreateDTO;
import ru.yandex.practicum.dto.UserLoginDTO;
import static io.restassured.RestAssured.given;
import static ru.yandex.practicum.constants.Constants.*;

public class UserSteps {
    @Step("Создание уникального пользователя")
    public ValidatableResponse userCreate(UserCreateDTO userCreateDTO){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userCreateDTO)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("Создание дублирующего пользователя")
    public ValidatableResponse userDuplicateCreate(UserCreateDTO userCreateDTO){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userCreateDTO)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("Создание пользователя без email")
    public ValidatableResponse userCreateWithoutEmail(UserCreateDTO userCreateDTO){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userCreateDTO)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("Создание пользователя без пароля")
    public ValidatableResponse userCreateWithoutPassword(UserCreateDTO userCreateDTO){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userCreateDTO)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("Создание пользователя без имени")
    public ValidatableResponse userCreateWithoutName(UserCreateDTO userCreateDTO){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userCreateDTO)
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
    public ValidatableResponse userLogin(UserLoginDTO userLoginDTO){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userLoginDTO)
                .when()
                .post(LOG_USER_PATH)
                .then();
    }

    @Step("Авторизация пользователя с неверным логином")//Ave Maria! Deus Vult!
    public ValidatableResponse userLoginWhitIncorrectLogin(UserLoginDTO userLoginDTO){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userLoginDTO)
                .when()
                .post(LOG_USER_PATH)
                .then();
    }

    @Step("Авторизация пользователя с неверным паролем")//Ave Maria! Deus Vult!
    public ValidatableResponse userLoginWhitIncorrectPassword(UserLoginDTO userLoginDTO){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userLoginDTO)
                .when()
                .post(LOG_USER_PATH)
                .then();
    }

    @Step("Изменение данных пользователя")
    public ValidatableResponse userUpDateData(UserChangeDataDTO userChangeDataDTO, String token){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(userChangeDataDTO)
                .when()
                .patch(CHANGE_USER_DATA_OR_DELETE_OR_GET_DATA_PATH)
                .then();
    }

    @Step("Изменение данных пользователя без авторизации")
    public ValidatableResponse userUpDateDataWithoutLogin(UserChangeDataDTO userChangeDataDTO){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .header("Content-type", "application/json")
                .body(userChangeDataDTO)
                .when()
                .patch(CHANGE_USER_DATA_OR_DELETE_OR_GET_DATA_PATH)
                .then();
    }

}
