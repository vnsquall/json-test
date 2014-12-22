import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.json.mapping.JsonPathObjectDeserializer;
import com.jayway.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.get;
import static org.testng.Assert.assertEquals;

/**
 * Created by vnsquall on 12/19/14.
 */
public class TestAPIGetTeasers {
    @BeforeTest
    public void setup(){
        RestSetup.Setup();
    }

    @Test
    public void test_001_AndroidTeasers(){
        Response res = get("main/getteasers/");
        assertEquals(200, res.getStatusCode());

        String categoriesJson = res.asString();
        JsonPath jp = new JsonPath(categoriesJson).setRoot("metadata");
        ArrayList<String> android_prod_urls = jp.get("data.data.attributes.image_list.product_url");

        System.out.println(android_prod_urls.toString());

    }

//    @Test
//    public void test_002_iOSTeasers(){
//        Response res = get("main/getteasersios/");
//        assertEquals(200, res.getStatusCode());
//
//        String categoriesJson = res.asString();
//        JsonPath jp = new JsonPath(categoriesJson).setRoot("metadata");
//        List<String> ios_prod_urls = jp.get("data.data.product_url");
//
//        System.out.println(ios_prod_urls);
//        for(String prod_url : prod_urls){
//            res = get(prod_url);
//            assertEquals(200, res.getStatusCode());
//            System.out.println("Get iOS Teaser: "+prod_url+" successful");
//        }
//    }
}
