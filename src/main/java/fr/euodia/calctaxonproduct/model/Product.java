package fr.euodia.calctaxonproduct.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

@Schema(description = "Représente un produit avec ses informations de base")
public class Product {
    @Schema(description = "ID unique du produit (généré automatiquement)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private final Long id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    @Schema(description = "Nom du produit", example = "Smartphone XYZ")
    private final String name;

    @NotNull(message = "Le prix ne peut pas être null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être positif")
    @Schema(description = "Prix du produit (hors taxes)", example = "299.99")
    private final BigDecimal price;

    @NotNull(message = "Le pays ne peut pas être null")
    @Schema(description = "Pays pour le calcul des taxes", example = "FRANCE")
    private final Country country;

    @JsonCreator
    public Product(@JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("country") Country country) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Country getCountry() {
        return country;
    }
}
