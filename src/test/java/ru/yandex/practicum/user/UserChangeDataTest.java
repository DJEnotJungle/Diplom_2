package ru.yandex.practicum.user;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.yandex.practicum.UrlBeforeTest;
import ru.yandex.practicum.jsonclass.UserChangeDataJson;
import ru.yandex.practicum.jsonclass.UserCreateJson;
import ru.yandex.practicum.jsonclass.UserLoginJson;
import ru.yandex.practicum.steps.UserSteps;

@DisplayName("Тесты изменения данных пользователя")
public class UserChangeDataTest extends UrlBeforeTest {

    UserSteps userSteps = new UserSteps();
    private String token;
    private String tokenForMethod;
    UserCreateJson userCreate = new UserCreateJson("asdfgr1322@gmail.com", "asd123", "das");
    UserLoginJson userLogin = new UserLoginJson("asdfgr1322@gmail.com", "asd123");
    UserChangeDataJson userChanged = new UserChangeDataJson("asdfgr132212324@gmail.com","das123");

    @Test
    @DisplayName("Тест изменения данных пользователя с авторизацией")
    public void userChangeDataWithLoginTest(){
        ValidatableResponse responseCreate = userSteps.userCreate(userCreate);
        token=responseCreate.extract().path("accessToken");
        ValidatableResponse responseLogin = userSteps.userLogin(userLogin);
        tokenForMethod=responseLogin.extract().path("accessToken");
        ValidatableResponse responseChange = userSteps.userUpDateData(userChanged, tokenForMethod);
        responseChange.assertThat()
                .statusCode(200)
                .and()
                .body("success", Matchers.equalTo(true))
                .and()
                .body("user.email", Matchers.equalTo(userChanged.getEmail()))
                .and()
                .body("user.name", Matchers.equalTo(userChanged.getName()));
    }

    @Test
    @DisplayName("Тест изменения данных пользователя без авторизацией")
    public void userChangeDataWithoutLoginTest(){
        ValidatableResponse responseCreate = userSteps.userCreate(userCreate);
        token=responseCreate.extract().path("accessToken");
        ValidatableResponse responseChange = userSteps.userUpDateDataWithoutLogin(userChanged);
        responseChange.assertThat()
                .statusCode(401)
                .and()
                .body("success", Matchers.equalTo(false))
                .and()
                .body("message", Matchers.equalTo("You should be authorised"));
    }

    @After
    @Step("Проверяем надо ли удалить пользователя")
    public void deleteUser(){
        if (!(token == null || token.isEmpty())) {
            userSteps.userDelete(token);
        }
    }
}
