package fr.euodia.calctaxonproduct.service;

import fr.euodia.calctaxonproduct.dto.FinalPriceDto;
import fr.euodia.calctaxonproduct.exception.ProductNotFoundException;
import fr.euodia.calctaxonproduct.factories.tax.TaxStrategy;
import fr.euodia.calctaxonproduct.factories.tax.TaxStrategyFactory;
import fr.euodia.calctaxonproduct.model.Country;
import fr.euodia.calctaxonproduct.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests du service Product")
class ProductServiceTest {

    @Mock
    private TaxStrategyFactory taxStrategyFactory;

    @Mock
    private TaxStrategy taxStrategy;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(taxStrategyFactory);
    }

    @Test
    @DisplayName("Doit ajouter un produit avec un ID généré automatiquement")
    void shouldAddProductWithGeneratedId() {
        Product inputProduct = new Product(null, "Test Product", new BigDecimal("100.00"), Country.FRANCE);
        Product result = productService.addProduct(inputProduct);

        assertNotNull(result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(new BigDecimal("100.00"), result.getPrice());
        assertEquals(Country.FRANCE, result.getCountry());
    }

    @Test
    @DisplayName("Doit générer des IDs uniques pour chaque produit")
    void shouldGenerateUniqueIds() {
        Product product1 = new Product(null, "Product 1", new BigDecimal("100.00"), Country.FRANCE);
        Product product2 = new Product(null, "Product 2", new BigDecimal("200.00"), Country.CANADA);
        Product result1 = productService.addProduct(product1);
        Product result2 = productService.addProduct(product2);

        assertNotEquals(result1.getId(), result2.getId());
    }

    @Test
    @DisplayName("Doit lever une exception si le produit à ajouter est null")
    void shouldThrowExceptionWhenAddingNullProduct() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.addProduct(null));

        assertEquals("Le produit ne peut pas être null", exception.getMessage());
    }

    @Test
    @DisplayName("Doit récupérer un produit par son ID")
    void shouldGetProductById() {
        Product inputProduct = new Product(null, "Test Product", new BigDecimal("100.00"), Country.FRANCE);
        Product addedProduct = productService.addProduct(inputProduct);
        Product result = productService.getProductById(addedProduct.getId());

        assertEquals(addedProduct, result);
    }

    @Test
    @DisplayName("Doit lever une exception si le produit n'est pas trouvé")
    void shouldThrowExceptionWhenProductNotFound() {
        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(999L));

        assertEquals("Produit non trouvé avec l'ID: 999", exception.getMessage());
    }

    @Test
    @DisplayName("Doit lever une exception si l'ID est null lors de la récupération")
    void shouldThrowExceptionWhenGetProductByIdWithNullId() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.getProductById(null));

        assertEquals("L'ID ne peut pas être null", exception.getMessage());
    }

    @Test
    @DisplayName("Doit calculer le prix final avec les taxes")
    void shouldCalculateFinalPrice() {
        Product inputProduct = new Product(null, "Test Product", new BigDecimal("100.00"), Country.FRANCE);
        Product addedProduct = productService.addProduct(inputProduct);

        when(taxStrategyFactory.getStrategy(Country.FRANCE)).thenReturn(taxStrategy);
        when(taxStrategy.calculateTax(any(Product.class))).thenReturn(new BigDecimal("20.00"));

        FinalPriceDto result = productService.calculateFinalPrice(addedProduct.getId());

        assertEquals(addedProduct.getId(), result.getProductId());
        assertEquals(new BigDecimal("100.00"), result.getOriginalPrice());
        assertEquals(new BigDecimal("20.00"), result.getTaxAmount());
        assertEquals(new BigDecimal("120.00"), result.getFinalPrice());

        verify(taxStrategyFactory).getStrategy(Country.FRANCE);
        verify(taxStrategy).calculateTax(addedProduct);
    }

    @Test
    @DisplayName("Doit lever une exception si le produit n'existe pas lors du calcul du prix final")
    void shouldThrowExceptionWhenCalculatingFinalPriceForNonExistentProduct() {
        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.calculateFinalPrice(999L));

        assertEquals("Produit non trouvé avec l'ID: 999", exception.getMessage());
    }

    @Test
    @DisplayName("Doit lever une exception si l'ID est null lors du calcul du prix final")
    void shouldThrowExceptionWhenCalculatingFinalPriceWithNullId() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.calculateFinalPrice(null));

        assertEquals("L'ID ne peut pas être null", exception.getMessage());
    }
}
