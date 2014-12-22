import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.get;
import static org.testng.Assert.assertEquals;

/**
 * Created by vnsquall on 12/19/14.
 */
public class TestAPIGetMainMd5 {
    @BeforeTest
    public void setup(){
        RestSetup.Setup();
    }

    @DataProvider
    Object[][] getAPIFromMainMd5(){
        return new Object[][]{
                {"main/fetchdata/"},
                {"main/slider/"},
                {"main/imageresolutions/"},
                {"catalog/brands/"}
        };
    }

    @Test(dataProvider = "getAPIFromMainMd5")
    public void test_APIFromMainMd5(String main_url){
        Response res = get(main_url);
        assertEquals(200, res.getStatusCode());
        System.out.println("Get Main URL: "+main_url+" successful");
    }
}
