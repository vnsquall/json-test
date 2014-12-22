import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static org.testng.Assert.assertEquals;

/**
 * Created by vnsquall on 12/19/14.
 */
public class TestAPIGetAndroidTeasers {
    @BeforeTest
    public void setup(){
        RestSetup.Setup();
    }

    @DataProvider(name = "getAndroidTeasers")
    public Object[][] getAndroidTeasers() {
        Response res = get("main/getteasers/");
        assertEquals(200, res.getStatusCode());
        String teasersJson = res.asString();

        JsonPath jp = new JsonPath(teasersJson).setRoot("metadata");
        List<ArrayList<ArrayList<String>>> android_prod_urls = jp.get("data.data.attributes.image_list.product_url");
        ArrayList<ArrayList<String>> urls = android_prod_urls.get(0);

        Object[][] data = new Object[urls.size()][];
        int index = 0;

        for(ArrayList<String> url : urls){
            String prod_url = url.get(0);
            data[index] = new Object[]{prod_url};
            index++;
        }
        return data;
    }

    @Test(dataProvider = "getAndroidTeasers")
    public void test_001_AndroidTeasers(String url) {
        Response res = get(url);
        assertEquals(200, res.getStatusCode());
        if (res.getStatusCode() == 200) {
            System.out.println("Get Android Teaser: " + url + " PASSED");
        } else {
            System.out.println("Get Android Teaser: " + url + " FAILED");
        }
    }
}
