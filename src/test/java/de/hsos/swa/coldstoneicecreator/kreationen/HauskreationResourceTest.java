//@author Stefan Bierenriede 852142

package de.hsos.swa.coldstoneicecreator.kreationen;

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
public class HauskreationResourceTest {

    @TestHTTPResource("/api/hauskreationen")
    URL hauskreationenEndpoint;

    @Test
    @Order(45)
    public void testGetUnauthorized(){
        given().contentType(ContentType.JSON).when().get(hauskreationenEndpoint)
            .then().statusCode(401);
    }

    @Test
    @Order(46)
    public void testPostUnauthorized() {
        given().contentType(ContentType.JSON).when().post(hauskreationenEndpoint)
            .then().statusCode(401);
    }

    @Test
    @Order(47)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdmin(){
        given().contentType(ContentType.JSON).when().get(hauskreationenEndpoint)
            .then().statusCode(200)
            .body("size()", is(14));
    }

    @Test
    @Order(48)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testPostAdmin(){
        given().contentType(ContentType.JSON).when().post(hauskreationenEndpoint)
            .then().statusCode(400);
    }
}
