import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static org.testng.Assert.assertEquals;

/**
 * Created by vnsquall on 12/22/14.
 */
public class TestAPIGetForms {
    @BeforeTest
    public void setup() {
        RestSetup.Setup();
    }

    @DataProvider(name = "getFormsRequest")
    public Object[][] getStaticPages() {
        Response res = get("forms/index/");
        assertEquals(200, res.getStatusCode());
        String teasersJson = res.asString();

        JsonPath jp = new JsonPath(teasersJson).setRoot("metadata");
        List<String> urls = jp.get("data.url");

        Object[][] data = new Object[urls.size()][];
        int index = 0;
        for (String url : urls) {
            data[index] = new Object[]{url};
            index++;
        }
        return data;
    }

    @Test
    public void test_FormsRequest(String url) {
        Response res = get(url);
        assertEquals(200, res.getStatusCode());
        if (res.getStatusCode() == 200) {
            System.out.println("Get Form: " + url + " PASSED");
        } else {
            System.out.println("Get Form: " + url + " FAILED");
        }
    }
}
