package fr.euodia.calctaxonproduct.factories.tax.implementation;

import fr.euodia.calctaxonproduct.model.Country;
import fr.euodia.calctaxonproduct.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la stratégie de taxation Canada")
class CanadaTaxStrategyTest {

    private CanadaTaxStrategy canadaTaxStrategy;

    @BeforeEach
    void setUp() {
        canadaTaxStrategy = new CanadaTaxStrategy();
    }

    @Test
    @DisplayName("Doit calculer correctement la taxe Canada (GST 5% + PST 7% = 12%)")
    void shouldCalculateCanadaTaxCorrectly() {
        Product product = new Product(1L, "Test Product", new BigDecimal("100.00"), Country.CANADA);
        BigDecimal tax = canadaTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("12.00"), tax);
    }

    @Test
    @DisplayName("Doit calculer correctement la taxe avec des décimales")
    void shouldCalculateCanadaTaxWithDecimals() {
        Product product = new Product(1L, "Test Product", new BigDecimal("99.99"), Country.CANADA);
        BigDecimal tax = canadaTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("12.00"), tax);
    }

    @Test
    @DisplayName("Doit calculer correctement les composants GST et PST")
    void shouldCalculateGstAndPstComponents() {
        Product product = new Product(1L, "Test Product", new BigDecimal("50.00"), Country.CANADA);
        BigDecimal tax = canadaTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("6.00"), tax);
    }

    @Test
    @DisplayName("Doit lever une exception si le produit est null")
    void shouldThrowExceptionWhenProductIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> canadaTaxStrategy.calculateTax(null));

        assertEquals("Le produit ne peut pas être null", exception.getMessage());
    }

    @Test
    @DisplayName("Doit calculer une taxe zéro pour un prix zéro")
    void shouldCalculateZeroTaxForZeroPrice() {
        Product product = new Product(1L, "Free Product", BigDecimal.ZERO, Country.CANADA);
        BigDecimal tax = canadaTaxStrategy.calculateTax(product);

        assertEquals(new BigDecimal("0.00"), tax);
    }
}
