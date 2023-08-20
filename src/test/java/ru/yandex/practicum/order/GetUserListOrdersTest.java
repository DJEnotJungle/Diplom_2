package ru.yandex.practicum.order;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.yandex.practicum.UrlBeforeTest;
import ru.yandex.practicum.dto.OrderDTO;
import ru.yandex.practicum.dto.UserCreateDTO;
import ru.yandex.practicum.steps.OrderSteps;
import ru.yandex.practicum.steps.UserSteps;

@DisplayName("Тест получения списка заказов пользователя")
public class GetUserListOrdersTest extends UrlBeforeTest {
    OrderSteps orderSteps = new OrderSteps();
    UserSteps userSteps = new UserSteps();
    private String token;
    UserCreateDTO userCreate = new UserCreateDTO("vsej8gbdkgirg@gmail.com", "pak9sagir", "Sergey");
    OrderDTO orderWithIngredients = new OrderDTO(new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa75"});

    @Test
    @DisplayName("Тест получение списка заказов авторизованного пользователя")
    public void orderGetUserListTest(){
        ValidatableResponse responseCreate = userSteps.userCreate(userCreate);
        token=responseCreate.extract().path("accessToken");
        ValidatableResponse responseOrder = orderSteps.orderCreate(orderWithIngredients, token);
        ValidatableResponse responseOrderList = orderSteps.orderUserGet(token);
        responseOrderList.assertThat()
                .statusCode(200)
                .and()
                .body("success", Matchers.equalTo(true))
                .and()
                .body("orders.ingredients", Matchers.notNullValue())
                .and()
                .body("orders._id", Matchers.notNullValue())
                .and()
                .body("orders.status", Matchers.notNullValue())
                .and()
                .body("orders.number", Matchers.notNullValue())
                .and()
                .body("orders.createdAt", Matchers.notNullValue())
                .and()
                .body("orders.updatedAt", Matchers.notNullValue())
                .and()
                .body("total", Matchers.notNullValue())
                .and()
                .body("totalToday", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Тест получение списка заказов неавторизованного пользователя")
    public void orderGetUserListWithoutLoginTest(){
        ValidatableResponse responseCreate = userSteps.userCreate(userCreate);
        token=responseCreate.extract().path("accessToken");
        ValidatableResponse responseOrder = orderSteps.orderCreate(orderWithIngredients, token);
        ValidatableResponse responseOrderList = orderSteps.orderUserGetWithoutLogin();
        responseOrderList
                .statusCode(401)
                .and()
                .body("success", Matchers.equalTo(false))
                .and()
                .body("message", Matchers.equalTo( "You should be authorised"));
    }

    @After
    @Step("Проверяем надо ли удалить пользователя")
    public void deleteUser(){
        if (!(token == null || token.isEmpty())) {
            userSteps.userDelete(token);
        }
    }
}
