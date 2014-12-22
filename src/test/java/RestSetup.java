import com.jayway.restassured.RestAssured;

/**
 * Created by vnsquall on 12/19/14.
 */
public class RestSetup {
    public static void Setup(){
        RestAssured.baseURI = "https://www.lazada.sg";
        RestAssured.basePath = "/mobapi/";
        RestAssured.config().getRedirectConfig().followRedirects(true);
    }
}
