package ru.yandex.practicum.user;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.yandex.practicum.dto.UserCreateDTO;
import ru.yandex.practicum.steps.UserSteps;
import ru.yandex.practicum.UrlBeforeTest;

@DisplayName("Тесты создания пользователя")
public class UserCreateTest extends UrlBeforeTest {

    UserSteps userSteps = new UserSteps();
    private String token;
    UserCreateDTO userUnique = new UserCreateDTO("vwer22y@gmail.com","asddsa123443","OlegIvagfnov");
    UserCreateDTO userWithoutEmail = new UserCreateDTO("","test1", "test");
    UserCreateDTO userWithoutPassword = new UserCreateDTO("test1@mail.ru","", "test");
    UserCreateDTO userWithoutName = new UserCreateDTO("test1@mail.ru","test1", "");

    @Test
    @DisplayName("Тест создания уникального пользователя")
    public void testCreateUniqueUser(){
        ValidatableResponse response = userSteps.userCreate(userUnique);
        token=response.extract().path("accessToken");
        response.assertThat()
                .statusCode(200)
                .and()
                .body("success", Matchers.equalTo(true))
                .and()
                .body("user.email", Matchers.equalTo(userUnique.getEmail()))
                .and()
                .body("user.name", Matchers.equalTo(userUnique.getName()));
    }

    @Test
    @DisplayName("Тест создания уже зарегистрированного пользователя")
    public void testCreateDuplicateUser(){
        ValidatableResponse response = userSteps.userCreate(userUnique);
        token=response.extract().path("accessToken");
        ValidatableResponse responseDuplicate = userSteps.userDuplicateCreate(userUnique);
        responseDuplicate.assertThat()
                .statusCode(403)
                .and()
                .body("success", Matchers.equalTo(false))
                .and()
                .body("message", Matchers.equalTo("User already exists"));
    }

    @Test
    @DisplayName("Тест создания пользователя без email")
    public void testCreateUserWithoutEmail(){
        ValidatableResponse response = userSteps.userCreateWithoutEmail(userWithoutEmail);
        token=response.extract().path("accessToken");
        response.assertThat()
                .statusCode(403)
                .and()
                .body("success", Matchers.equalTo(false))
                .and()
                .body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Тест создания пользователя без пароля")
    public void testCreateUserWithoutPassword(){
        ValidatableResponse response = userSteps.userCreateWithoutPassword(userWithoutPassword);
        token=response.extract().path("accessToken");
        response.assertThat()
                .statusCode(403)
                .and()
                .body("success", Matchers.equalTo(false))
                .and()
                .body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Тест создания пользователя без имени")
    public void testCreateUserWithoutName(){
        ValidatableResponse response = userSteps.userCreateWithoutName(userWithoutName);
        token=response.extract().path("accessToken");
        response.assertThat()
                .statusCode(403)
                .and()
                .body("success", Matchers.equalTo(false))
                .and()
                .body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @After
    @Step("Проверяем надо ли удалить пользователя")
    public void deleteUser(){
        if (!(token == null || token.isEmpty())) {
            userSteps.userDelete(token);
        }
    }

}

