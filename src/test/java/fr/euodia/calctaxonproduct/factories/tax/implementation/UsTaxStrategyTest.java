package fr.euodia.calctaxonproduct.factories.tax.implementation;

import fr.euodia.calctaxonproduct.model.Country;
import fr.euodia.calctaxonproduct.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la stratégie de taxation US")
class UsTaxStrategyTest {

    private UsTaxStrategy usTaxStrategy;

    @BeforeEach
    void setUp() {
        usTaxStrategy = new UsTaxStrategy();
    }

    @Test
    @DisplayName("Doit calculer correctement la taxe US (8.5%)")
    void shouldCalculateUsTaxCorrectly() {
        Product product = new Product(1L, "Test Product", new BigDecimal("100.00"), Country.UNITED_STATES);
        BigDecimal tax = usTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("8.50"), tax);
    }

    @Test
    @DisplayName("Doit calculer correctement la taxe avec des décimales")
    void shouldCalculateUsTaxWithDecimals() {
        Product product = new Product(1L, "Test Product", new BigDecimal("99.99"), Country.UNITED_STATES);
        BigDecimal tax = usTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("8.50"), tax);
    }

    @Test
    @DisplayName("Doit arrondir correctement les taxes")
    void shouldRoundTaxCorrectly() {
        Product product = new Product(1L, "Test Product", new BigDecimal("10.01"), Country.UNITED_STATES);
        BigDecimal tax = usTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("0.85"), tax);
    }

    @Test
    @DisplayName("Doit lever une exception si le produit est null")
    void shouldThrowExceptionWhenProductIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usTaxStrategy.calculateTax(null));

        assertEquals("Le produit ne peut pas être null", exception.getMessage());
    }

    @Test
    @DisplayName("Doit calculer une taxe zéro pour un prix zéro")
    void shouldCalculateZeroTaxForZeroPrice() {
        Product product = new Product(1L, "Free Product", BigDecimal.ZERO, Country.UNITED_STATES);
        BigDecimal tax = usTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("0.00"), tax);
    }
}
