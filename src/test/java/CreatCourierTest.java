import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import requestOjects.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static steps.URI.setUpURI;

import steps.CourierStep;

public class CreatCourierTest {
    Courier courier = new Courier("courierSasha", "12345", "Sasha");
    CourierStep step = new CourierStep();

    @Before
    public void setUp() {
        setUpURI();
    }

    @Test
    public void createCourierPositiveTest() {
        step.creatCourier(courier)
                .then()
                .statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    public void createIdenticalCouriersTest() {
        step.creatCourier(courier)
                .then()
                .statusCode(201);
        step.creatCourier(courier)
                .then()
                .statusCode(409);
    }

    @Test
    public void createEqualCourierWithTheSameLoginTest() {
        step.creatCourier(courier)
                .then()
                .statusCode(201);
        Courier courier1 = new Courier(courier.getLogin(), "Aa12345", courier.getFirstName());
        step.creatCourier(courier1)
                .then()
                .statusCode(409);

    }

    @Test
    public void createCourierWithoutLoginTest() throws JsonProcessingException {
        String json = step.getJsonWithTwoParams("password", "12345", "firstName", "Test");
        step.creatCourierWithJson(json)
                .then()
                .statusCode(400);
    }

    @Test
    public void createCourierWithoutPasswordTest() throws JsonProcessingException {
        String json = step.getJsonWithTwoParams("login", "courierSasha", "firstName", "Test");
        step.creatCourierWithJson(json)
                .then()
                .statusCode(400);
    }

    @After
    public void clearCourierLogin() {
        String courierId = step.getCourierId(courier);
        step.deleteCourier(courierId);
    }
}
