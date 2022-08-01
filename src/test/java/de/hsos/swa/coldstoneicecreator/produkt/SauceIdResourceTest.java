//@author Stefan Bierenriede 852142

package de.hsos.swa.coldstoneicecreator.produkt;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import de.hsos.swa.coldstoneicecreator.produkt.boundary.dto.SauceDTO;
import de.hsos.swa.coldstoneicecreator.produkt.entity.Allergene;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(OrderAnnotation.class)
public class SauceIdResourceTest {
    
    @Test
    @Order(9)
    public void testGetNotFound(){
        given().contentType(ContentType.JSON).when().get("/api/saucen/0")
            .then().statusCode(401);
    }

    @Test
    @Order(10)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetFound(){
        given().contentType(ContentType.JSON).when().get("/api/saucen/1")
            .then().statusCode(200);
    }

    @Test
    @Order(11)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testPutEis(){
        Set<Allergene> allergene = new HashSet<>();
        allergene.add(Allergene.EI);
        given().contentType(ContentType.JSON).body(new SauceDTO(null, "test", allergene))
        .when().put("/api/saucen/1")
        .then().statusCode(200);
    }

    @Test
    @Order(12)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void deleteForbidden(){
        given().contentType(ContentType.JSON).when().delete("/api/saucen/9")
            .then().statusCode(500);
    }

    @Test
    @Order(13)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void deleteAllowed(){
        given().contentType(ContentType.JSON).when().delete("/api/saucen/1")
            .then().statusCode(204);
    }
}
