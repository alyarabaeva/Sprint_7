import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requestOjects.Courier;
import static org.hamcrest.Matchers.*;

import static org.hamcrest.Matchers.equalTo;
import steps.CourierStep;

public class CourierLoginTest {
    Courier courier = new Courier("courierSasha", "12345", "Sasha");
    CourierStep step = new CourierStep();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
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
    public void courierLoginIncorrectLoginTest(){
        String json = "{\"login\": \"sasha\", \"password\": \"" + courier.getPassword() +"\"}";
        step.courierLoginWithJson(json)
                .then()
                .statusCode(404);
    }

    @Test
    public void courierLoginIncorrectPasswordTest(){
        String json = "{\"login\": \""+ courier.getLogin()+ "\", \"password\": \"1234\"}";
        step.courierLoginWithJson(json)
                .then()
                .statusCode(404);
    }
    @Test
    public void courierLoginWithoutPasswordTest(){
        String json = "{\"login\": \""+ courier.getLogin()+ "\"}";
        step.courierLoginWithJson(json)
                .then()
                .statusCode(504);
    }

    @Test
    public void courierLoginWithoutLoginTest(){
        String json = "{\"password\": \""+ courier.getPassword()+ "\"}";
        step.courierLoginWithJson(json)
                .then()
                .statusCode(400);
    }

    @Test
    public void courierLoginWithUnrealCourierTest(){
        String json = "{\"login\": \"unrealCourier\", \"password\": \"Test\"}";
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
