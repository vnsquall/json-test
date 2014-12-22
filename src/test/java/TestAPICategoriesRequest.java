import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

/**
 * Created by vnsquall on 12/16/14.
 */
public class TestAPICategoriesRequest {
    @BeforeTest
    public void setup(){
        RestSetup.Setup();
    }

    @Test //Get the categories' info & all main categories
    public void test_GetCatalogCategories(){
        Response res = get("catalog/categories/?light=true");
        assertEquals(200, res.getStatusCode());
        String categoriesJson = res.asString();
        JsonPath jp = new JsonPath(categoriesJson).setRoot("metadata");
        List<String> main_url_api = jp.get("data.api_url");
        List<Integer> productCounts = jp.get("data.product_count");

        for (Integer i=0; i<main_url_api.size();i++){
            String main_url = main_url_api.get(i).toString();
            String prodCount = productCounts.get(i).toString();
            res = get(main_url);
            assertEquals(200, res.getStatusCode());

            String mainURLJson = res.asString();
            JsonPath mainJP = new JsonPath(mainURLJson).setRoot("metadata");
            String mainProdCount = mainJP.get("product_count");
            System.out.println("Get " + main_url + " with Product Count: "
                    + prodCount
                    + " Return Prod_Count: " + mainProdCount);
        }
    }
}
