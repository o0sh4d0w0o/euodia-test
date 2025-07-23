package fr.euodia.calctaxonproduct.controller;

import fr.euodia.calctaxonproduct.dto.FinalPriceDto;
import fr.euodia.calctaxonproduct.model.Product;
import fr.euodia.calctaxonproduct.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Produits", description = "API de gestion des produits et calcul des taxes")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Créer un produit", description = "Crée un nouveau produit avec un ID généré automatiquement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produit créé avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Données de produit invalides", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Product> addProduct(
            @Valid @RequestBody @Parameter(description = "Informations du produit à créer", required = true) Product product) {
        Product createdProduct = productService.addProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un produit", description = "Récupère un produit par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit trouvé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Product> getProduct(
            @PathVariable("id") @Parameter(description = "ID du produit", required = true, example = "1") Long id) {
        Product product = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}/final-price")
    @Operation(summary = "Calculer le prix final", description = "Calcule le prix final d'un produit avec les taxes selon le pays")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prix final calculé avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FinalPriceDto.class))),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<FinalPriceDto> calculateFinalPrice(
            @PathVariable("id") @Parameter(description = "ID du produit", required = true, example = "1") Long id) {
        FinalPriceDto finalPrice = productService.calculateFinalPrice(id);

        return ResponseEntity.ok(finalPrice);
    }
}
