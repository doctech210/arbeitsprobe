package de.hsos.swa.coldstoneicecreator.bestellung;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
public class BestellungIdResourceTest {
    
    @Test
    public void testGet(){
        given().contentType(ContentType.JSON).when().get("/api/bestellungen/0")
            .then().statusCode(401);
    }

    @Test
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdmin(){
        given().contentType(ContentType.JSON).when().get("/api/bestellungen/0")
            .then().statusCode(404);
    }

    
}
