package de.hsos.swa.coldstoneicecreator.bestellung;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.net.URL;

@QuarkusTest
public class BestellungResourceTest {

    @TestHTTPResource("/api/bestellungen")
    URL bestellEndpoint;

    @Test
    public void testGetUnauthorized(){
        given().contentType(ContentType.JSON).when().get(bestellEndpoint)
            .then().statusCode(401);
    }

    @Test
    public void testPostUnauthorized() {
        given().contentType(ContentType.JSON).when().post(bestellEndpoint)
            .then().statusCode(401);
    }

    @Test
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdmin(){
        given().contentType(ContentType.JSON).when().get(bestellEndpoint)
            .then().statusCode(200)
            .body("size()", is(0));
    }

    @Test
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testPostAdmin(){
        given().contentType(ContentType.JSON).when().post(bestellEndpoint)
            .then().statusCode(404);
    }
}
