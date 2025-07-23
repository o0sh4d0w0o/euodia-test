package fr.euodia.calctaxonproduct.service;

import fr.euodia.calctaxonproduct.dto.FinalPriceDto;
import fr.euodia.calctaxonproduct.exception.ProductNotFoundException;
import fr.euodia.calctaxonproduct.factories.tax.TaxStrategy;
import fr.euodia.calctaxonproduct.factories.tax.TaxStrategyFactory;
import fr.euodia.calctaxonproduct.model.Product;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final TaxStrategyFactory taxStrategyFactory;
    private final ConcurrentHashMap<Long, Product> productRepository;
    private final AtomicLong nextId;

    public ProductService(TaxStrategyFactory taxStrategyFactory) {
        this.taxStrategyFactory = taxStrategyFactory;
        this.productRepository = new ConcurrentHashMap<>();
        this.nextId = new AtomicLong(1L); // Used this to generate pseudo-unique IDs for products but could be replaced
                                          // with an UUID when using a database to ensure unique IDs
    }

    public Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }

        Long generatedId = nextId.getAndIncrement();
        Product productWithId = new Product(
                generatedId,
                product.getName(),
                product.getPrice(),
                product.getCountry());

        productRepository.put(generatedId, productWithId);

        return productWithId;
    }

    public Product getProductById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Product product = productRepository.get(id);

        if (product == null) {
            throw new ProductNotFoundException("Produit non trouvé avec l'ID: " + id);
        }

        return product;
    }

    public FinalPriceDto calculateFinalPrice(Long productId) {
        Product product = getProductById(productId);

        TaxStrategy taxStrategy = taxStrategyFactory.getStrategy(product.getCountry());
        BigDecimal taxAmount = taxStrategy.calculateTax(product);
        BigDecimal finalPrice = product.getPrice().add(taxAmount);

        return new FinalPriceDto(
                product.getId(),
                product.getPrice(),
                taxAmount,
                finalPrice);
    }
}
