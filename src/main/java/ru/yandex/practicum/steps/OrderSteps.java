package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.models.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    public static final String ORDER = "/api/v1/orders";
    public static final String CANCEL = "/api/v1/orders/cancel";

    @Step("Create order with POST request to /api/v1/orders")
    public ValidatableResponse createOrder (Order order){
        return given()
                .body(order)
                .when()
                .post(ORDER)
                .then();
    }

    @Step("Delete order with PUT request to /api/v1/orders/cancel")
    public ValidatableResponse deleteOrder (Order order){
        return given()
                .when()
                .put(CANCEL + "?track=" +order.getTrack())
                .then();
    }

    @Step("Receiving order with POST request to /api/v1/orders")
    public ValidatableResponse getOrderList (){
        return given()
                .when()
                .get(ORDER)
                .then();
    }
}
