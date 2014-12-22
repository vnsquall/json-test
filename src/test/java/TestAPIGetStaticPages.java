import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static org.testng.Assert.assertEquals;

/**
 * Created by vnsquall on 12/22/14.
 */
public class TestAPIGetStaticPages {
    @BeforeTest
    public void setup() {
        RestSetup.Setup();
    }

    @DataProvider(name = "getStaticPages")
    public Iterator<Object[]> getStaticPages() {
        Response res = get("main/getstaticblocks/");
        assertEquals(200, res.getStatusCode());
        String teasersJson = res.asString();

        JsonPath jp = new JsonPath(teasersJson).setRoot("metadata");
        List<String> urls = jp.get("data.url");
        List<Object[]> data = new ArrayList<Object[]>();

        for (String url : urls) {
            data.add(new Object[]{url});
        }
        return data.iterator();
    }

    @Test(dataProvider = "getStaticPages")
    public void test_APIGetStaticBlocks(String url) {
        Response res = get(url);
        assertEquals(200, res.getStatusCode());
        System.out.println("Get Static Page: " + url + " successful");
    }
}
