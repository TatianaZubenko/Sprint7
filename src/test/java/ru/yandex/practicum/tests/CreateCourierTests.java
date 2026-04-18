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

public class CreateCourierTests extends BaseTest {

    private Courier courier;
    private CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier = new Courier();
        courier.withLogin(RandomStringUtils.randomAlphabetic(12))
                .withPassword(RandomStringUtils.randomAlphabetic(10))
                .withFirstName(RandomStringUtils.randomAlphabetic(11));
    }

    @Test
    @DisplayName("Курьера можно создать")
    public void shouldCreateCourier() {

        courierSteps.createCourier(courier)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void shouldNotCreateDoubleCourier() {

        courierSteps.createCourier(courier);
        courierSteps.createCourier(courier)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Попытка создать курьера без логина")
    public void shouldNotCreateCourierWithoutLogin() {

        courier.withLogin("");
        courierSteps.createCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Попытка создать курьера без пароля")
    public void shouldNotCreateCourierWithoutPassword() {

        courier.withPassword("");
        courierSteps.createCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void shouldNotCreateCourierWithExistingLogin() {

        courierSteps.createCourier(courier);
        courier.withPassword(RandomStringUtils.randomAlphabetic(10))
                .withFirstName(RandomStringUtils.randomAlphabetic(11));
        courierSteps.createCourier(courier)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void tearDown() {
        courier.withId(courierSteps
                .loginCourier(courier)
                .extract().body().path("id"));
        courierSteps.deleteCourier(courier);
    }
}
