package api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;


public class BaseTest {

    @BeforeTest
    public void setup(){
        RestAssured.baseURI = ApiConfig.BASE_URL;
    }
}
