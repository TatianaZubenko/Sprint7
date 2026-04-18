package ru.yandex.practicum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.models.Courier;
import ru.yandex.practicum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTests extends BaseTest{

    private Courier courier;
    private CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier = new Courier();
        courier.withLogin(RandomStringUtils.randomAlphabetic(12))
                .withPassword(RandomStringUtils.randomAlphabetic(10))
                .withFirstName(RandomStringUtils.randomAlphabetic(11));
        courierSteps.createCourier(courier);
    }

    @Test
    @DisplayName("Курьер может авторизоваться, введены все обязательные поля")
    public void shouldLoginCourierTest() {

        courierSteps.loginCourier(courier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Система вернёт ошибку, если неправильно указать логин")
    public void shouldNotLoginCourierTestAnotherLogin() {

        courier.withLogin(RandomStringUtils.randomAlphabetic(12));
        courierSteps.loginCourier(courier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Система вернёт ошибку, если неправильно указать пароль")
    public void shouldNotLoginCourierTestAnotherPassword() {

        courier.withPassword(RandomStringUtils.randomAlphabetic(10));
        courierSteps.loginCourier(courier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Система вернёт ошибку, если не указать логин")
    public void shouldNotLoginCourierTestWithoutLogin() {

        courier.withLogin("");
        courierSteps.loginCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Система вернёт ошибку, если не указать пароль")
    public void shouldNotLoginCourierTestWithoutPassword() {

        courier.withPassword("");
        courierSteps.loginCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void shouldNotLoginCourierTestAnotherLoginAndPassword() {

        courier.withLogin(RandomStringUtils.randomAlphabetic(12))
                .withPassword(RandomStringUtils.randomAlphabetic(10));
        courierSteps.loginCourier(courier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        courier.withId(courierSteps
                .loginCourier(courier)
                .extract().body().path("id"));
        courierSteps.deleteCourier(courier);
    }
}
