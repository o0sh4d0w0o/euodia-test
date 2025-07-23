package fr.euodia.calctaxonproduct.integration;

import fr.euodia.calctaxonproduct.dto.FinalPriceDto;
import fr.euodia.calctaxonproduct.model.Country;
import fr.euodia.calctaxonproduct.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Tests d'intégration de l'API REST Product")
class ProductIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Doit créer un produit et calculer son prix final avec succès")
    void shouldCreateProductAndCalculateFinalPrice() {
        Product productToCreate = new Product(null, "Test Product", new BigDecimal("100.00"), Country.FRANCE);
        ResponseEntity<Product> createResponse = restTemplate.postForEntity("/products", productToCreate,
                Product.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());
        assertEquals("Test Product", createResponse.getBody().getName());
        assertEquals(0, new BigDecimal("100.00").compareTo(createResponse.getBody().getPrice()));
        assertEquals(Country.FRANCE, createResponse.getBody().getCountry());

        Long productId = createResponse.getBody().getId();
        ResponseEntity<Product> getResponse = restTemplate.getForEntity("/products/" + productId, Product.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(productId, getResponse.getBody().getId());
        assertEquals("Test Product", getResponse.getBody().getName());
        assertEquals(0, new BigDecimal("100.00").compareTo(getResponse.getBody().getPrice()));
        assertEquals(Country.FRANCE, getResponse.getBody().getCountry());

        ResponseEntity<FinalPriceDto> finalPriceResponse = restTemplate.getForEntity(
                "/products/" + productId + "/final-price",
                FinalPriceDto.class);

        assertEquals(HttpStatus.OK, finalPriceResponse.getStatusCode());
        assertNotNull(finalPriceResponse.getBody());

        FinalPriceDto finalPrice = finalPriceResponse.getBody();

        assertEquals(productId, finalPrice.getProductId());
        assertEquals(0, new BigDecimal("100.00").compareTo(finalPrice.getOriginalPrice()));
        assertEquals(0, new BigDecimal("20.00").compareTo(finalPrice.getTaxAmount()));
        assertEquals(0, new BigDecimal("120.00").compareTo(finalPrice.getFinalPrice()));
    }

    @Test
    @DisplayName("Doit calculer correctement les taxes pour différents pays")
    void shouldCalculateTaxesForDifferentCountries() {
        Product usProduct = new Product(null, "US Product", new BigDecimal("100.00"), Country.UNITED_STATES);
        ResponseEntity<Product> usCreateResponse = restTemplate.postForEntity("/products", usProduct,
                Product.class);

        assertEquals(HttpStatus.CREATED, usCreateResponse.getStatusCode());

        Long usProductId = usCreateResponse.getBody().getId();
        ResponseEntity<FinalPriceDto> usFinalPrice = restTemplate.getForEntity(
                "/products/" + usProductId + "/final-price",
                FinalPriceDto.class);

        assertEquals(HttpStatus.OK, usFinalPrice.getStatusCode());
        assertEquals(0, new BigDecimal("8.50").compareTo(usFinalPrice.getBody().getTaxAmount()));
        assertEquals(0, new BigDecimal("108.50").compareTo(usFinalPrice.getBody().getFinalPrice()));

        Product canadaProduct = new Product(null, "Canada Product", new BigDecimal("100.00"), Country.CANADA);
        ResponseEntity<Product> canadaCreateResponse = restTemplate.postForEntity("/products", canadaProduct,
                Product.class);

        assertEquals(HttpStatus.CREATED, canadaCreateResponse.getStatusCode());

        Long canadaProductId = canadaCreateResponse.getBody().getId();
        ResponseEntity<FinalPriceDto> canadaFinalPrice = restTemplate.getForEntity(
                "/products/" + canadaProductId + "/final-price",
                FinalPriceDto.class);

        assertEquals(HttpStatus.OK, canadaFinalPrice.getStatusCode());
        assertEquals(0, new BigDecimal("12.00").compareTo(canadaFinalPrice.getBody().getTaxAmount()));
        assertEquals(0, new BigDecimal("112.00").compareTo(canadaFinalPrice.getBody().getFinalPrice()));
    }

    @Test
    @DisplayName("Doit retourner 404 pour un produit inexistant")
    void shouldReturn404ForNonExistentProduct() {
        ResponseEntity<String> response = restTemplate.getForEntity("/products/999", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Doit retourner 404 lors du calcul du prix final pour un produit inexistant")
    void shouldReturn404WhenCalculatingFinalPriceForNonExistentProduct() {
        ResponseEntity<String> response = restTemplate.getForEntity("/products/999/final-price", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
