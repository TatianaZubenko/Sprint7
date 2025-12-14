package ru.yandex.practicum.steps;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.models.Courier;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    public static final String COURIER = "/api/v1/courier";
    public static final String LOGIN = "/api/v1/courier/login";

    @Step("Send POST request to /api/v1/courier")
    public ValidatableResponse createCourier(Courier courier){

        return given()
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }

    @Step("Send POST request to /api/v1/courier/login")
    public ValidatableResponse loginCourier(Courier courier) {

        return given()
                .body(courier)
                .when()
                .post(LOGIN)
                .then();
    }

    @Step("Delete courier with POST request to /api/v1/courier")
    public ValidatableResponse deleteCourier(Courier courier) {
        return given()
                .when()
                .delete(COURIER + courier.getId())
                .then();
    }
}
