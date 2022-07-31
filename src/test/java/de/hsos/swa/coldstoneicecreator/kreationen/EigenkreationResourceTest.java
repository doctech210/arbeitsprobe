package de.hsos.swa.coldstoneicecreator.kreationen;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.KreationIdDTO;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@QuarkusTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(OrderAnnotation.class)
public class EigenkreationResourceTest {

    @TestHTTPResource("/api/eigenkreationen")
    URL eigenkreationenEndpoint;

    @Test
    @Order(25)
    public void testGetUnauthorized(){
        given().contentType(ContentType.JSON).when().get(eigenkreationenEndpoint)
            .then().statusCode(401);
    }

    @Test
    @Order(26)
    public void testPostUnauthorized() {
        given().contentType(ContentType.JSON).when().post(eigenkreationenEndpoint)
            .then().statusCode(401);
    }

    @Test
    @Order(27)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdminEmpty(){
        given().contentType(ContentType.JSON).when().get(eigenkreationenEndpoint)
            .then().statusCode(200)
            .body("size()", is(0));
    }

    @Test
    @Order(28)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testPostAdmin(){
        List<Long> zutaten = new ArrayList<>();
        zutaten.add(Long.valueOf(1));
        zutaten.add(Long.valueOf(23));
        given().contentType(ContentType.JSON).body(new KreationIdDTO(Long.valueOf(1), Long.valueOf(5), zutaten, Long.valueOf(5), Long.valueOf(1), "test"))
            .when().post(eigenkreationenEndpoint).then().statusCode(200);
    }

    @Test
    @Order(29)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testPostKunde(){
        List<Long> zutaten = new ArrayList<>();
        zutaten.add(Long.valueOf(46));
        zutaten.add(Long.valueOf(15));
        given().contentType(ContentType.JSON).body(new KreationIdDTO(Long.valueOf(1), Long.valueOf(5), zutaten, Long.valueOf(5), Long.valueOf(1), "test"))
            .when().post(eigenkreationenEndpoint).then().statusCode(200);
    }

    @Test
    @Order(30)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdminFull(){
        given().contentType(ContentType.JSON).when().get(eigenkreationenEndpoint)
            .then().statusCode(200)
            .body("size()", is(2));
    }
}
