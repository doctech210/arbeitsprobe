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
public class EigenkreationIdResourceTest {
    
    @Test
    @Order(31)
    public void testGet(){
        given().contentType(ContentType.JSON).when().get("/api/eigenkreationen/0")
            .then().statusCode(401);
    }

    @Test
    @Order(32)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdmin(){
        given().contentType(ContentType.JSON).when().get("/api/eigenkreationen/1")
            .then().statusCode(404);
    }

    @Test
    @Order(33)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testGetKunde(){
        given().contentType(ContentType.JSON).when().get("/api/eigenkreationen/1")
            .then().statusCode(404);
    }

    @Test
    @Order(34)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testPutEigenkreationKunde(){
        List<Long> zutaten = new ArrayList<>();
        zutaten.add(Long.valueOf(46));
        zutaten.add(Long.valueOf(15));
        given().contentType(ContentType.JSON).body(new KreationIdDTO(Long.valueOf(2), Long.valueOf(5), zutaten, Long.valueOf(6), Long.valueOf(1), "testNeu"))
        .when().put("/api/eigenkreationen/1")
        .then().statusCode(200);
    }

    @Test
    @Order(35)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testPostKunde(){
        given().contentType(ContentType.JSON).body(Long.valueOf(100))
            .when().post("/api/eigenkreationen/1")
            .then().statusCode(200);
    }

    @Test
    @Order(36)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testPutZutatenKunde(){
        given().contentType(ContentType.JSON).body(Long.valueOf(20))
            .when().put("/api/eigenkreationen/1/zutaten/1")
            .then().statusCode(200);   
    }

    @Test
    @Order(37)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testDeleteKunde(){
        given().contentType(ContentType.JSON).body(Long.valueOf(1)).when()
            .delete("/api/eigenkreationen/1").then().statusCode(404);
            //TODO: Gucken wie wir das machen?
    }
}
