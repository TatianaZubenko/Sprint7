package ru.yandex.practicum.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.models.Order;
import ru.yandex.practicum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderTest extends BaseTest {

    private Order order = new Order();
    private OrderSteps orderSteps = new OrderSteps();
    private Integer track;

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        order.withFirstName(RandomStringUtils.randomAlphabetic(10))
                .withLastName(RandomStringUtils.randomAlphabetic(10))
                .withAddress(RandomStringUtils.randomAlphabetic(10))
                .withMetroStation(Integer.parseInt(RandomStringUtils.randomNumeric(1)))
                .withPhone(RandomStringUtils.randomNumeric(11))
                .withRentTime(Integer.parseInt(RandomStringUtils.randomNumeric(1)))
                .withDeliveryDate("2025-12-31")
                .withComment(RandomStringUtils.randomAlphabetic(30))
                .withColor(new String[]{""});
    }

    @Test
    public void getOrder() {
        track = orderSteps.createOrder(order)
                        .extract().body().path("track");
                orderSteps.getOrderList()
                .statusCode(200)
                .body("orders", notNullValue());
    }

    @After
    public void tearDown() {
        order.withTrack(track);
        orderSteps.deleteOrder(order);
    }
}
