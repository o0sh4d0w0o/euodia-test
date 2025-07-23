package fr.euodia.calctaxonproduct.factories.tax.implementation;

import fr.euodia.calctaxonproduct.model.Country;
import fr.euodia.calctaxonproduct.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la stratégie de taxation France")
class FranceTaxStrategyTest {

    private FranceTaxStrategy franceTaxStrategy;

    @BeforeEach
    void setUp() {
        franceTaxStrategy = new FranceTaxStrategy();
    }

    @Test
    @DisplayName("Doit calculer correctement la TVA")
    void shouldCalculateFranceTaxCorrectly() {
        Product product = new Product(1L, "Test Product", new BigDecimal("100.00"), Country.FRANCE);
        BigDecimal tax = franceTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("20.00"), tax);
    }

    @Test
    @DisplayName("Doit calculer correctement la TVA avec des décimales")
    void shouldCalculateFranceTaxWithDecimals() {
        Product product = new Product(1L, "Test Product", new BigDecimal("99.99"), Country.FRANCE);
        BigDecimal tax = franceTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("20.00"), tax);
    }

    @Test
    @DisplayName("Doit arrondir correctement les taxes")
    void shouldRoundTaxCorrectly() {
        Product product = new Product(1L, "Test Product", new BigDecimal("12.50"), Country.FRANCE);
        BigDecimal tax = franceTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("2.50"), tax);
    }

    @Test
    @DisplayName("Doit lever une exception si le produit est null")
    void shouldThrowExceptionWhenProductIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> franceTaxStrategy.calculateTax(null));

        assertEquals("Le produit ne peut pas être null", exception.getMessage());
    }

    @Test
    @DisplayName("Doit calculer une taxe zéro pour un prix zéro")
    void shouldCalculateZeroTaxForZeroPrice() {
        Product product = new Product(1L, "Free Product", BigDecimal.ZERO, Country.FRANCE);
        BigDecimal tax = franceTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("0.00"), tax);
    }
}
