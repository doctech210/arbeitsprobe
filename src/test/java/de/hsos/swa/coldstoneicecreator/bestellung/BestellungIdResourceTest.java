//@author Stefan Bierenriede 852142

package de.hsos.swa.coldstoneicecreator.bestellung;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(OrderAnnotation.class)
public class BestellungIdResourceTest {
    
    @Test
    @Order(49)
    public void testGet(){
        given().contentType(ContentType.JSON).when().get("/api/bestellungen/0")
            .then().statusCode(401);
    }

    @Test
    @Order(50)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdmin(){
        given().contentType(ContentType.JSON).when().get("/api/bestellungen/0")
            .then().statusCode(404);
    }

    
}
