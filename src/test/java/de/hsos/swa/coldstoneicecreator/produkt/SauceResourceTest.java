package de.hsos.swa.coldstoneicecreator.produkt;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.net.URL;

@QuarkusTest
public class SauceResourceTest {

    @TestHTTPResource("/api/saucen")
    URL saucenEndpoint;

    @Test
    public void testGetUnauthorized(){
        given().contentType(ContentType.JSON).when().get(saucenEndpoint)
            .then().statusCode(401);
    }

    @Test
    public void testPostUnauthorized() {
        given().contentType(ContentType.JSON).when().post(saucenEndpoint)
            .then().statusCode(401);
    }

    @Test
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdmin(){
        given().contentType(ContentType.JSON).when().get(saucenEndpoint)
            .then().statusCode(200)
            .body("size()", is(14));
    }

    // @Test
    // @TestSecurity(user= "admin", roles = {"Admin"})
    // public void testPostAdmin(){
    //     given().contentType(ContentType.JSON).when().post(hauskreationenEndpoint)
    //         .then().statusCode(404);
    // }
}
