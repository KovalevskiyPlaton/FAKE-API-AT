package tests;

import io.restassured.http.ContentType;
import api.model.UserData;
import org.testng.annotations.Test;
import utils.ApiConfig;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIdtest(){

        List<UserData> users = given()
                .queryParam("key", ApiConfig.API_KEY)
                .when()
                .log().all()
                .contentType(ContentType.JSON)
                .get(URL+ "api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        int i = 0;
    }
}
