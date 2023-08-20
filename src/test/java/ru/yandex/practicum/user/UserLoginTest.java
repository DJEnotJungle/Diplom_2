package ru.yandex.practicum.user;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.yandex.practicum.UrlBeforeTest;
import ru.yandex.practicum.dto.UserCreateDTO;
import ru.yandex.practicum.dto.UserLoginDTO;
import ru.yandex.practicum.steps.UserSteps;

@DisplayName("Тесты авторизации пользователя")
public class UserLoginTest extends UrlBeforeTest {

    UserSteps userSteps = new UserSteps();
    private String token;
    UserCreateDTO userCreate = new UserCreateDTO("v12eryasdsdf1234@gmail.com", "pasagir", "Sergey");
    UserLoginDTO userExisting = new UserLoginDTO("v12eryasdsdf1234@gmail.com", "pasagir");
    UserLoginDTO userWithIncorrectLogin = new UserLoginDTO("v12eryasdsdf123@gmail.com", "pasagir");
    UserLoginDTO userWithIncorrectPassword = new UserLoginDTO("v12eryasdsdf1234@gmail.com", "pasagi");

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void testLoginUser(){
        ValidatableResponse responseCreate = userSteps.userCreate(userCreate);
        token=responseCreate.extract().path("accessToken");
        ValidatableResponse responseLogin = userSteps.userLogin(userExisting);
        responseLogin.assertThat()
                .statusCode(200)
                .and()
                .body("success", Matchers.equalTo(true))
                .and()
                .body("user.email", Matchers.equalTo(userExisting.getEmail()))
                .and()
                .body("user.name", Matchers.equalTo(userCreate.getName()));
    }

    @Test
    @DisplayName("Логин с неверным логином")
    public void testLoginUserWithIncorrectLogin(){
        ValidatableResponse responseCreate = userSteps.userCreate(userCreate);
        token=responseCreate.extract().path("accessToken");
        ValidatableResponse responseLogin = userSteps.userLoginWhitIncorrectLogin(userWithIncorrectLogin);
        responseLogin.assertThat()
                .statusCode(401)
                .and()
                .body("success", Matchers.equalTo(false))
                .and()
                .body("message", Matchers.equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void testLoginUserWithIncorrectPassword(){
        ValidatableResponse responseCreate = userSteps.userCreate(userCreate);
        token=responseCreate.extract().path("accessToken");
        ValidatableResponse responseLogin = userSteps.userLoginWhitIncorrectPassword(userWithIncorrectPassword);
        responseLogin.assertThat()
                .statusCode(401)
                .and()
                .body("success", Matchers.equalTo(false))
                .and()
                .body("message", Matchers.equalTo("email or password are incorrect"));
    }

    @After
    @Step("Проверяем надо ли удалить пользователя")
    public void deleteUser(){
        if (!(token == null || token.isEmpty())) {
            userSteps.userDelete(token);
        }
    }
}
