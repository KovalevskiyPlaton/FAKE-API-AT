package tests;

import api.model.RegisterUser;
import api.model.SuccessReg;
import api.model.UnSuccessReg;
import api.protect.Specifications;
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
    public void checkAvatarAndIdtest() {
        Specifications.installSpecification(Specifications.reqestSpec(URL), Specifications.responseSpecOk200());
        List<UserData> users = given().queryParam("key", ApiConfig.API_KEY).when().log().all()
                .get("api/users?page=2").then().log().all().extract().body().jsonPath()
                .getList("data", UserData.class);

        /*Проверка: id пользователя  встречается в аватаре пользователя */
        users.stream().forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));

        /*Проверка: e-mail каждого пользователя заканчивается на '@reqres.in' */
        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

        /*Проверка: id пользователя  встречается в аватаре пользователя  - Вариант 2 (через коллекции)*/
        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());

        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void succesRegTest() {
        Specifications.installSpecification(Specifications.reqestSpec(URL), Specifications.responseSpecOk200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";

        RegisterUser user = new RegisterUser("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given().queryParam("key", ApiConfig.API_KEY).body(user).when()
                .post("api/register").then().log().all().extract().as(SuccessReg.class);
        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());

        Assert.assertEquals(id, successReg.getId());
        Assert.assertEquals(token, successReg.getToken());
    }

    @Test
    public void unSuccessRegTest() {
        Specifications.installSpecification(Specifications.reqestSpec(URL), Specifications.responseSpecError400());
        RegisterUser user = new RegisterUser("sydney@fife", "");
        UnSuccessReg unSuccessReg = given().body(user).post("api/register").then().log().all()
                .extract().as(UnSuccessReg.class);

        Assert.assertEquals("Missing password", unSuccessReg.getError());
    }
}
