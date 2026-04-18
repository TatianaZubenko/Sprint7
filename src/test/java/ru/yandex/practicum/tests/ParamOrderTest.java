package ru.yandex.practicum.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.models.Order;
import ru.yandex.practicum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class ParamOrderTest extends BaseTest {

    private Order order = new Order();
    private OrderSteps orderSteps = new OrderSteps();
    private Integer track;

    public ParamOrderTest(String[] color) {
        order.withColor(color);
    }


    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                { new String[]{"BLACK"} },
                { new String[]{"GREY"} },
                { new String[]{"BLACK","GREY"} },
                { new String[]{""} },
        };
    }

    @Before
    public void setUp () {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        order.withFirstName(RandomStringUtils.randomAlphabetic(10))
                .withLastName(RandomStringUtils.randomAlphabetic(10))
                .withAddress(RandomStringUtils.randomAlphabetic(10))
                .withMetroStation(Integer.parseInt(RandomStringUtils.randomNumeric(1)))
                .withPhone(RandomStringUtils.randomNumeric(11))
                .withRentTime(Integer.parseInt(RandomStringUtils.randomNumeric(1)))
                .withDeliveryDate("2025-12-31")
                .withComment(RandomStringUtils.randomAlphabetic(30));
    }

    @Test
    public void createOrder(){
        track =
        orderSteps.createOrder(order)
                .statusCode(201)
                .body("track", notNullValue())
                .extract().body().path("track");
    }

    @After
    public void tearDown(){
        order.withTrack(track);
        orderSteps.deleteOrder(order);
    }

}
