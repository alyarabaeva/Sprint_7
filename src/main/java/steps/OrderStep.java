package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import requestOjects.Order;

import static io.restassured.RestAssured.given;

public class OrderStep {
    @Step("Creat new order")
    public Response creatOrder(Order order) {
        return given()
                .header("Content-type", "application/json; charset=utf-8")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Accept order")
    public Response acceptOrder(String orderId, String courierId){
        return given()
                .queryParam("courierId", courierId)
                .put("/api/v1/orders/accept/" + orderId);
    }

    @Step("Cancel order")
    public Response cancelOrder(String track){
        String json = "{\"track\": \""+ track+ "\"}";
        return given()
                .header("Content-type", "application/json; charset=utf-8")
                .body(json)
                .put("/api/v1/orders/cancel");
    }

}
