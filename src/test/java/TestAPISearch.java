import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by vnsquall on 12/22/14.
 */
public class TestAPISearch {
    @BeforeTest
    public void setup() {
        RestSetup.Setup();
    }

    @DataProvider(name = "getQueryForSearch")
    Object[][] getQueryForSearch() {
        return new Object[][]{
                {"TV"},
                {"Apple"},
                {"Congacon"}
        };
    }

    @Test(dataProvider = "getQueryForSearch")
    public void test_Search(String query) {
        Response res =
                given().
                        param("q", query).
                        param("page", 1).
                        param("maxitems", 10).

                        when().
                        get("search/");

        assertEquals(200, res.getStatusCode());
        String searchJson = res.asString();
        JsonPath jp = new JsonPath(searchJson).setRoot("");

        HashMap msg = jp.get("messages");
        if (query.equals("Congacon")) {
            Boolean isSuccess = jp.get("success");
            assertTrue(isSuccess == false);
            System.out.println(msg.get("error"));
        } else if (query.equals("TV") | query.equals("Apple")) {
            Boolean isSuccess = jp.get("success");
            assertTrue(isSuccess == true);
            System.out.println(msg.get("success"));
        }
    }

    @Test(dataProvider = "getQueryForSearch")
    public void test_SearchSuggestion(String query) {
        Response res =
                given().
                        param("q", query).

                        when().
                        get("search/");

        assertEquals(200, res.getStatusCode());
        String searchJson = res.asString();
        JsonPath jp = new JsonPath(searchJson).setRoot("");

        HashMap msg = jp.get("messages");
        if (query.equals("Congacon")) {
            Boolean isSuccess = jp.get("success");
            assertTrue(isSuccess == false);
            System.out.println(msg.get("error"));
        } else if (query.equals("TV") | query.equals("Apple")) {
            Boolean isSuccess = jp.get("success");
            assertTrue(isSuccess == true);
            System.out.println(msg.get("success"));
        }
    }

    @DataProvider(name = "getDataForFind")
    Object[][] getDataForFind() {
        return new Object[][]{
                {"Apple"},
                {"Sony"},
                {"Congacon"}
        };
    }

    @Test(dataProvider = "getDataForFind")
    public void test_FindForProduct(String query) {
        Response res =
                given().
                        param("q", query).
                        param("sort", "popularity").
                        param("dir", "desc").

                        when().
                        get("search/");

        assertEquals(200, res.getStatusCode());
        String searchJson = res.asString();
        JsonPath jp = new JsonPath(searchJson).setRoot("");

        HashMap msg = jp.get("messages");
        if (query.equals("Congacon")) {
            Boolean isSuccess = jp.get("success");
            assertTrue(isSuccess == false);
            System.out.println(msg.get("error"));
        } else if (query.equals("Sony") | query.equals("Apple")) {
            Boolean isSuccess = jp.get("success");
            assertTrue(isSuccess == true);
            System.out.println(msg.get("success"));
        }
    }
}
