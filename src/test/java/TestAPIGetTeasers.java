
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.List;

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

        String teasersJson = res.asString();
        JsonPath jp = new JsonPath(teasersJson).setRoot("metadata");
        List<ArrayList<ArrayList<String>>> android_prod_urls = jp.get("data.data.attributes.image_list.product_url");
//        System.out.println(android_prod_urls);

        //Only get the first part of data response
        ArrayList<ArrayList<String>> urls = android_prod_urls.get(0);

//        System.out.println(urls.toString());
        for(ArrayList<String> url : urls){
            //Only get the url
            String and_prod_url = url.get(0);
            res = get(and_prod_url);
            assertEquals(200, res.getStatusCode());
            System.out.println("Get android Teaser: "+and_prod_url+" successful");
        }
    }

    @Test
    public void test_002_iOSTeasers(){
        Response res = get("main/getteasersios/");
        assertEquals(200, res.getStatusCode());

        String categoriesJson = res.asString();
        JsonPath jp = new JsonPath(categoriesJson).setRoot("metadata");
        List<ArrayList<ArrayList<String>>> ios_prod_urls = jp.get("data.data.attributes.image_list.product_url");
//        System.out.println(ios_prod_urls);

        ArrayList<ArrayList<String>> urls = ios_prod_urls.get(0);
        for(ArrayList<String> url : urls){
            //Only get the url
            String ios_prod_url = url.get(0);
            res = get(ios_prod_url);
            assertEquals(200, res.getStatusCode());
            System.out.println("Get iOS Teaser: "+ios_prod_url+" successful");
        }
    }
}
