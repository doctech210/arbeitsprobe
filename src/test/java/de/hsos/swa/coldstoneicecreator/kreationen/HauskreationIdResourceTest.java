//@author Stefan Bierenriede 852142

package de.hsos.swa.coldstoneicecreator.kreationen;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto.KreationIdDTO;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(OrderAnnotation.class)
public class HauskreationIdResourceTest {
    
    @Test
    @Order(38)
    public void testGetUnauthorized(){
        given().contentType(ContentType.JSON).when().get("/api/hauskreationen/1")
            .then().statusCode(401);
    }

    @Test
    @Order(39)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testGetKunde(){
        given().contentType(ContentType.JSON).when()
            .get("/api/hauskreationen/1")
            .then().statusCode(200);  
    }

    @Test
    @Order(40)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdmin(){
        given().contentType(ContentType.JSON).when()
            .get("/api/hauskreationen/1")
            .then().statusCode(200);  
    }

    @Test
    @Order(41)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testPutAdmin(){
        List<Long> zutaten = new ArrayList<>();
        zutaten.add(Long.valueOf(3));
        zutaten.add(Long.valueOf(15));
        given().contentType(ContentType.JSON).body(new KreationIdDTO(Long.valueOf(5), Long.valueOf(2), zutaten, Long.valueOf(6), Long.valueOf(2), "testNeu"))
           .when().put("/api/hauskreationen/1")
           .then().statusCode(200);
    }

    @Test
    @Order(42)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testPutKunde(){
        List<Long> zutaten = new ArrayList<>();
        zutaten.add(Long.valueOf(3));
        zutaten.add(Long.valueOf(15));
        given().contentType(ContentType.JSON).body(new KreationIdDTO(Long.valueOf(5), Long.valueOf(2), zutaten, Long.valueOf(6), Long.valueOf(2), "testNeu"))
           .when().put("/api/hauskreationen/1")
           .then().statusCode(403);
    }

    @Test
    @Order(43)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testPostKunde(){
        given().contentType(ContentType.JSON).body(Long.valueOf(100))
            .when().post("/api/hauskreationen/1")
            .then().statusCode(200);
    }

    @Test
    @Order(44)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testDeleteAdmin(){
        given().contentType(ContentType.JSON).when()
            .delete("/api/hauskreationen/5")
            .then().statusCode(204);
    }

}
