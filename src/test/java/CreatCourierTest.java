import requestOjects.Courier;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

import steps.CourierStep;

public class CreatCourierTest {
    Courier courier = new Courier("courierSasha", "12345", "Sasha");
    CourierStep step = new CourierStep();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
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
    public void createCourierWithoutLoginTest() {
        String json = "{\"password\": \"12345\", \"firstName\": \"Test\"}";
        step.creatCourierWithJson(json)
                .then()
                .statusCode(400);
    }

    @Test
    public void createCourierWithoutPasswordTest() {
        String json = "{\"login\": \"courierSasha\", \"firstName\": \"Test\"}";
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
