import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static org.testng.Assert.assertEquals;

/**
 * Created by vnsquall on 12/19/14.
 */
public class TestAPIGetAppInitCall {
    @BeforeTest
    public void setup(){
        RestSetup.Setup();
    }

    @DataProvider
    Object[][] getMobileAppInitCallAPI(){
        return new Object[][]{
                {"main/md5/"},
                {"forms/index"},
                {"main/getstatic?key=mobile_navigation"},
                {"main/getstatic?key=api_categories"},
                {"main/getstatic?key=mobile_categories_icons"},
                {"main/getstatic?key=faq_ios"}
        };
    }

    @Test(dataProvider = "getMobileAppInitCallAPI")
    public void test_001_InitAPICall(String uriAPI){
        Response res = get(uriAPI);
        assertEquals(200, res.getStatusCode());
        System.out.println("Get Init Call for: "+uriAPI+" successful");
    }

    @Test
    public void test_002_FormsRequest(){
        Response res = get("forms/index");
        assertEquals(200, res.getStatusCode());

        String formsJson = res.asString();
        JsonPath jp = new JsonPath(formsJson).setRoot("metadata");
        List<String> form_urls = jp.get("data.url");

        for(String form_url : form_urls){
            res = get(form_url);
            assertEquals(200, res.getStatusCode());
            System.out.println("Get form: "+form_url+" successful");
        }
    }
}
