//@author Stefan Bierenriede 852142

package de.hsos.swa.coldstoneicecreator.bestellung;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.net.URL;

@QuarkusTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(OrderAnnotation.class)
public class BestellungResourceTest {

    @TestHTTPResource("/api/bestellungen")
    URL bestellEndpoint;

    @Test
    @Order(51)
    public void testGetUnauthorized(){
        given().contentType(ContentType.JSON).when().get(bestellEndpoint)
            .then().statusCode(401);
    }

    @Test
    @Order(52)
    public void testPostUnauthorized() {
        given().contentType(ContentType.JSON).when().post(bestellEndpoint)
            .then().statusCode(401);
    }

    @Test
    @Order(53)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdmin(){
        given().contentType(ContentType.JSON).when().get(bestellEndpoint)
            .then().statusCode(200)
            .body("size()", is(0));
    }

    @Test
    @Order(54)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testPostAdmin(){
        given().contentType(ContentType.JSON).when().post(bestellEndpoint)
            .then().statusCode(404);
    }
}
