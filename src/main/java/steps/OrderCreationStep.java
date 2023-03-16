package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import requestOjects.Courier;
import requestOjects.Order;

import static io.restassured.RestAssured.given;

public class OrderCreationStep {
    @Step("Creat new order")
    public Response creatOrder(Order order) {
        return given()
                .header("Content-type", "application/json; charset=utf-8")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }
}
