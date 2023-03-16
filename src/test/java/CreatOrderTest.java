import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;
import static requestOjects.Colors.BLACK;
import static requestOjects.Colors.GREY;

import requestOjects.Order;
import steps.OrderCreationStep;

import java.util.List;

@RunWith(Parameterized.class)
public class CreatOrderTest {
    private final List<String> color;
    OrderCreationStep step = new OrderCreationStep();


    public CreatOrderTest(List<String> color) {
        this.color = color;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                new List[]{List.of("")},
                new List[]{List.of(GREY.name())},
                new List[]{List.of(BLACK.name())},
                new List[]{List.of(GREY.name(),BLACK.name())}
        };
    }

    @Test
    public void creatOrderTest() {
        Order newOrder = new Order("Sasha", "Yarabaeva", "Konoha, 142 apt.", "Sokol", "+7 000 00 000 00", 4, "2023-06-06", "Test", color);
        step.creatOrder(newOrder)
                .then()
                .statusCode(201)
                .and()
                .assertThat()
                .body("track", notNullValue());
    }
}
