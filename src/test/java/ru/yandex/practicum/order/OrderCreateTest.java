package ru.yandex.practicum.order;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.yandex.practicum.UrlBeforeTest;
import ru.yandex.practicum.jsonclass.OrderJson;
import ru.yandex.practicum.jsonclass.UserCreateJson;
import ru.yandex.practicum.jsonclass.UserLoginJson;
import ru.yandex.practicum.steps.OrderSteps;
import ru.yandex.practicum.steps.UserSteps;

@DisplayName("Тестирование создания заказа")
public class OrderCreateTest extends UrlBeforeTest {
    OrderSteps orderSteps = new OrderSteps();
    UserSteps userSteps = new UserSteps();
    private String token;
    private String tokenForMethod;
    UserCreateJson userCreate = new UserCreateJson("vsej8gbdkgirg@gmail.com", "pak9sagir", "Sergey");
    OrderJson orderWithIngredients = new OrderJson(new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa75"});
    UserLoginJson userLogin = new UserLoginJson("vsej8gbdkgirg@gmail.com", "pak9sagir");
    OrderJson orderWithoutIngredients = new OrderJson(new String[]{});
    OrderJson orderWithIncorrectHashOfIngredients = new OrderJson(new String[]{"61c0c5a71d1f00001bdaaa6d", "61c0c5a71d1f00001bdaaa6f", "61c0c5a71d1f0000bdaaa74"});

    @Test
    @DisplayName("Тест создания заказа авторизованным пользователем с ингредиентами")
    public void orderCreateWithLogin(){
        ValidatableResponse responseCreate = userSteps.userCreate(userCreate);
        token=responseCreate.extract().path("accessToken");
        ValidatableResponse responseLogin = userSteps.userLogin(userLogin);
        tokenForMethod=responseLogin.extract().path("accessToken");
        ValidatableResponse responseOrder = orderSteps.orderCreate(orderWithIngredients, tokenForMethod);
        responseOrder.assertThat()
                .statusCode(200)
                .and()
                .body("success", Matchers.equalTo(true))
                .and()
                .body("name", Matchers.notNullValue())
                .and()
                .body("order.number", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Тест создания заказа неавторизованным пользователем с ингридиентами")
    public void orderCreateWithoutLogin(){
        ValidatableResponse responseCreate = userSteps.userCreate(userCreate);
        token=responseCreate.extract().path("accessToken");
        ValidatableResponse responseOrder = orderSteps.orderCreateWithoutLogin(orderWithIngredients);
        responseOrder.assertThat()
                .statusCode(200)
                .and()
                .body("success", Matchers.equalTo(true))
                .and()
                .body("name", Matchers.notNullValue())
                .and()
                .body("order.number", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Тест создания заказа авторизованным пользователем без ингредиентов")
    public void orderCreateWithoutIngredients(){
        ValidatableResponse responseCreate = userSteps.userCreate(userCreate);
        token=responseCreate.extract().path("accessToken");
        ValidatableResponse responseLogin = userSteps.userLogin(userLogin);
        tokenForMethod=responseLogin.extract().path("accessToken");
        ValidatableResponse responseOrder = orderSteps.orderCreateWithOutIngredients(orderWithoutIngredients, tokenForMethod);
        responseOrder.assertThat()
                .statusCode(400)
                .and()
                .body("success", Matchers.equalTo(false))
                .and()
                .body("message", Matchers.equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Тест создания заказа неавторизованным пользователем с неверным хешем")
    public void orderCreateWithIncorrectHashOdIngredients(){
        ValidatableResponse responseCreate = userSteps.userCreate(userCreate);
        token=responseCreate.extract().path("accessToken");
        ValidatableResponse responseOrder = orderSteps.orderCreateWithIncorrectHash(orderWithIncorrectHashOfIngredients);
        responseOrder.assertThat()
                .statusCode(500);
    }

    @After
    @Step("Проверяем надо ли удалить пользователя")
    public void deleteUser(){
        if (!(token == null || token.isEmpty())) {
            userSteps.userDelete(token);
        }
    }

}
