import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requestOjects.Courier;
import requestOjects.Order;
import responseOjects.OrderId;
import steps.CourierStep;
import steps.OrderStep;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static steps.URI.setUpURI;

public class ListOfOrdersTest {
    CourierStep courierStep = new CourierStep();
    OrderStep orderStep = new OrderStep();
    Courier courier = new Courier("courierSasha", "12345", "Sasha");
    Order newOrder = new Order("Sasha", "Yarabaeva", "Konoha, 142 apt.", "Sokol", "+7 000 00 000 00", 4, "2023-06-06", "Test", List.of("GREY"));

    @Before
    public void setUp() {
        setUpURI();
        courierStep.creatCourier(courier)
                .then()
                .statusCode(201);
    }

    @Test
    public void getListOfOrdersTest() {
        String orderId = orderStep.creatOrder(newOrder).as(OrderId.class).getTrack();
        courierStep.getListOfOrders()
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
        //Очистка данных после теста
        orderStep.cancelOrder(orderId);
    }

    @Test
    public void getListOfOrdersForCourierTest() {
        String orderId = orderStep.creatOrder(newOrder).as(OrderId.class).getTrack();
        String courierId = courierStep.getCourierId(courier);
        orderStep.acceptOrder(orderId, courierId);

        courierStep.getListOfOrdersWithCourierId(courierId)
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
        //Очистка данных после теста
        orderStep.cancelOrder(orderId);
    }

    @After
    public void clearCourierLogin() {
        String courierId = courierStep.getCourierId(courier);
        courierStep.deleteCourier(courierId);
    }

}
