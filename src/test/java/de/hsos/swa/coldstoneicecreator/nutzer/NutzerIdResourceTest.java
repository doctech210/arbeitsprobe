package de.hsos.swa.coldstoneicecreator.nutzer;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import de.hsos.swa.coldstoneicecreator.nutzer.boundary.dto.NutzerImportDTO;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class NutzerIdResourceTest {
    
    @Test
    @Order(1)
    public void testGet(){
        given().contentType(ContentType.JSON).when().get("/api/nutzer/1")
            .then().statusCode(401);
    }

    @Test
    @Order(2)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testPostKundeAllowed(){
        given().contentType(ContentType.JSON).when()
            .get("/api/nutzer/1")
            .then().statusCode(200);
    }

    @Test
    @Order(3)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testPostKundeForbidden(){
        given().contentType(ContentType.JSON).when()
            .get("/api/nutzer/0")
            .then().statusCode(403);
    }

    @Test
    @Order(4)
    @TestSecurity(user= "user", roles = {"Kunde"})
    public void testPutKunde(){
        given().contentType(ContentType.JSON).body(new NutzerImportDTO(null, "John Doe", "test"))
        .when().put("/api/nutzer/1")
        .then().statusCode(200);
    }

    // @Test
    // @Order(5)
    // @TestSecurity(user= "user", roles = {"Kunde"})
    // public void testPostKunde(){
    //     given().contentType(ContentType.JSON).when()
    //         .delete("/api/nutzer/1")
    //         .then().statusCode(204);
    // }
}
