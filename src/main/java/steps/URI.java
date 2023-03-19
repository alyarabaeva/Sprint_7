package steps;

import io.restassured.RestAssured;

public class URI {
    public static final String PROD_URI = "https://qa-scooter.praktikum-services.ru";

    public static void setUpURI(){
        RestAssured.baseURI = PROD_URI;
    }
}
