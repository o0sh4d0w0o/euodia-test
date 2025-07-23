package fr.euodia.calctaxonproduct.controller;

import fr.euodia.calctaxonproduct.dto.FinalPriceDto;
import fr.euodia.calctaxonproduct.model.Product;
import fr.euodia.calctaxonproduct.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}/final-price")
    public ResponseEntity<FinalPriceDto> calculateFinalPrice(@PathVariable Long id) {
        FinalPriceDto finalPrice = productService.calculateFinalPrice(id);

        return ResponseEntity.ok(finalPrice);
    }
}
