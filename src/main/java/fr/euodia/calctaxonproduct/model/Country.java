package fr.euodia.calctaxonproduct.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Pays supportés pour le calcul des taxes")
public enum Country {
    @Schema(description = "États-Unis - Taxe de 8.5%")
    UNITED_STATES,

    @Schema(description = "Canada - Taxe de 12%")
    CANADA,

    @Schema(description = "France - TVA de 20%")
    FRANCE
}
