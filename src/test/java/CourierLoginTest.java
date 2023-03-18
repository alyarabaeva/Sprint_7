import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requestOjects.Courier;
import static org.hamcrest.Matchers.*;
import static steps.URI.PROD_URI;

import steps.CourierStep;

public class CourierLoginTest {
    Courier courier = new Courier("courierSasha", "12345", "Sasha");
    CourierStep step = new CourierStep();

    @Before
    public void setUp() {
        RestAssured.baseURI = PROD_URI;
        step.creatCourier(courier)
                .then()
                .statusCode(201);
    }

    @Test
    public void courierLoginPositiveTest() {
        step.courierLogin(courier)
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());
    }

    @Test
    public void courierLoginIncorrectLoginTest() throws JsonProcessingException {
        String json = step.getJsonWithTwoParams("login", "sasha", "password", courier.getPassword());
        step.courierLoginWithJson(json)
                .then()
                .statusCode(404);
    }

    @Test
    public void courierLoginIncorrectPasswordTest() throws JsonProcessingException {
        String json = step.getJsonWithTwoParams("login", courier.getLogin(), "password", "1234");
        step.courierLoginWithJson(json)
                .then()
                .statusCode(404);
    }
    @Test
    public void courierLoginWithoutPasswordTest() throws JsonProcessingException {
        String json = step.getJsonWithOneParam("login", courier.getLogin());
        step.courierLoginWithJson(json)
                .then()
                .statusCode(504);
    }

    @Test
    public void courierLoginWithoutLoginTest() throws JsonProcessingException {
        String json = step.getJsonWithOneParam("password", courier.getPassword());
        step.courierLoginWithJson(json)
                .then()
                .statusCode(400);
    }

    @Test
    public void courierLoginWithUnrealCourierTest() throws JsonProcessingException {
        String json = step.getJsonWithTwoParams("login", "unrealCourier", "password", "Test");
        step.courierLoginWithJson(json)
                .then()
                .statusCode(404);
    }

    @After
    public void clearCourierLogin() {
        String courierId = step.getCourierId(courier);
        step.deleteCourier(courierId);
    }

}
