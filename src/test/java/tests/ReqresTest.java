package tests;

import api.protect.Specifications;
import groovyjarjarpicocli.CommandLine;
import io.restassured.http.ContentType;
import api.model.UserData;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiConfig;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIdtest(){
        Specifications.installSpecification(Specifications.reqestSpec(URL), Specifications.responseSpecOk200());
        List<UserData> users = given()
                .queryParam("key", ApiConfig.API_KEY)
                .when()
                .log().all()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        /*Проверка: id пользователя  встречается в аватаре пользователя */
        users.stream().forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));

        /*Проверка: e-mail каждого пользователя заканчивается на '@reqres.in' */
        Assert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));

        /*Проверка: id пользователя  встречается в аватаре пользователя  - Вариант 2 (через коллекции)*/
        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x-> x.getId().toString()).collect(Collectors.toList());

        for(int i =0; i<avatars.size(); i++){
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }
}
