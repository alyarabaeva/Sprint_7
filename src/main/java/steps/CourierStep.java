package steps;

import io.qameta.allure.Step;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import requestOjects.Courier;
import requestOjects.CourierLogin;
import responseOjects.CourierId;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CourierStep {

    @Step("Creat new courier")
    public Response creatCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json; charset=utf-8")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Creat new courier")
    public Response creatCourierWithJson(String json) {
        return given()
                .header("Content-type", "application/json; charset=utf-8")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Login courier")
    public Response courierLogin(Courier courier) {
        CourierLogin courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        return given()
                .header("Content-type", "application/json; charset=utf-8")
                .body(courierLogin)
                .post("/api/v1/courier/login");
    }

    @Step("Login courier")
    public Response courierLoginWithJson(String json) {
        return given()
                .header("Content-type", "application/json; charset=utf-8")
                .body(json)
                .post("/api/v1/courier/login");
    }


    @Step("Get courier id")
    public String getCourierId(Courier courier) {
        return courierLogin(courier).body().as(CourierId.class).getId();
    }

    @Step("Delete courier from base")
    public void deleteCourier(String courierId) {
        given()
                .delete("/api/v1/courier/" + courierId);
    }

    @Step("Get list of orders for courier")
    public Response getListOfOrdersWithCourierId(String courierId) {
        return given()
                .queryParam("courierId", courierId)
                .get("/v1/orders");
    }

    @Step("Get list of orders")
    public Response getListOfOrders() {
        return given()
                .get("/v1/orders");
    }

    public String getJsonWithTwoParams(String param1, String value1, String param2, String value2) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        map.put(param1, value1);
        map.put(param2, value2);
        return objectMapper.writeValueAsString(map);
    }

    public String getJsonWithOneParam(String param1, String value1) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        map.put(param1, value1);
        return objectMapper.writeValueAsString(map);
    }
}
