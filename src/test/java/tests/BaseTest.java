package tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import utils.ApiConfig;


public class BaseTest {

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = ApiConfig.BASE_URL;
    }
}
