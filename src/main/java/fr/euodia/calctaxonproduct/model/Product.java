package fr.euodia.calctaxonproduct.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public class Product {
    private final Long id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private final String name;

    @NotNull(message = "Le prix ne peut pas être null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être positif")
    private final BigDecimal price;

    @NotNull(message = "Le pays ne peut pas être null")
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
