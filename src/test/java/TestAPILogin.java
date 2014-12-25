import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

/**
 * Created by vnsquall on 12/25/14.
 */
public class TestAPILogin {
    @BeforeTest
    public void setup() {
        RestSetup.Setup();
    }

    @DataProvider(name = "getCredential")
    Object[][] getQueryForSearch() {
        return new Object[][]{
                {"qa000@mail.com", "a12345"},
                {"uteste@mail.com", "a12345"}
        };
    }

    @Test(dataProvider = "getCredential")
    public void test_Login(String user, String pass) {
        Response res =
                given().
                        param("Alice_Module_Customer_Model_LoginForm[email]", user).
                        param("Alice_Module_Customer_Model_LoginForm[password]", pass).
                        when().
                        get("customer/login/");

        assertEquals(200, res.getStatusCode());
        String loginJson = res.asString();
        JsonPath jp = new JsonPath(loginJson).setRoot("");

        HashMap msg = jp.get("messages");
        Boolean isSuccess = jp.get("success");
        if (isSuccess == true) {
            System.out.println(msg.get("success"));
        } else {
            System.out.println(msg.get("error"));
        }
    }
}
