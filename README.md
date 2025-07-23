# API de Calcul des Taxes sur Produits

Une API REST développée avec Spring Boot pour la gestion des produits et le calcul des taxes selon les pays.

## Description

Cette API permet de :

-   Ajouter un nouveau produit.
-   Récupérer les détails d'un produit par son ID.
-   Calculer et retourner le prix final d'un produit incluant les taxes, basé sur le pays.

### Taxes supportées

-   **États-Unis** (**UNITED_STATES**) : 8,5% de taxe
-   **Canada** (**CANADA**) : 12% de taxe
-   **France** (**FRANCE**) : 20% de TVA

### Prérequis

-   Java 21 ou version supérieure
-   Git

### Installation et exécution

1. **Cloner le repository**

    ```bash
    git clone <repository-url>
    cd euodia-test
    ```

2. **Exécuter l'application**

    ```bash
    # Sur Windows
    .\gradlew bootRun

    # Sur Linux/macOS
    ./gradlew bootRun
    ```

3. **Accéder à l'application**
    - API : http://localhost:8080
    - Documentation Swagger : http://localhost:8080/swagger-ui.html
    - API Docs JSON : http://localhost:8080/api-docs

## Tests

### Exécuter tous les tests

```bash
# Sur Windows
.\gradlew test

# Sur Linux/macOS
./gradlew test
```

### Endpoints principaux

#### POST /products

Créer un nouveau produit

**Exemple de requête :**

```json
{
    "name": "Smartphone XYZ",
    "price": 299.99,
    "country": "FRANCE"
}
```

**Exemple de réponse (201 Created) :**

```json
{
    "id": 1,
    "name": "Smartphone XYZ",
    "price": 299.99,
    "country": "FRANCE"
}
```

#### GET /products/{id}

Récupérer un produit par son ID

**Exemple de réponse (200 OK) :**

```json
{
    "id": 1,
    "name": "Smartphone XYZ",
    "price": 299.99,
    "country": "FRANCE"
}
```

#### GET /products/{id}/final-price

Calculer le prix final avec taxes

**Exemple de réponse (200 OK) :**

```json
{
    "productId": 1,
    "originalPrice": 299.99,
    "taxAmount": 60.0,
    "finalPrice": 359.99
}
```

## Technologies utilisées

-   **Spring Boot 3.5.3** - Framework principal
-   **Spring Web** - API REST
-   **Spring Validation** - Validation des données
-   **SpringDoc OpenAPI 2.8.2** - Documentation Swagger
-   **JUnit 5** - Tests unitaires
-   **Spring Boot Test** - Tests d'intégration

## Gestion des erreurs

L'API retourne des erreurs structurées avec les codes HTTP appropriés :

-   **400 Bad Request** : Données invalides
-   **404 Not Found** : Ressource non trouvée
-   **500 Internal Server Error** : Erreur serveur

**Exemple de réponse d'erreur :**

```json
{
    "timestamp": "2025-07-23T14:30:00.123456",
    "status": 404,
    "error": "Produit non trouvé",
    "message": "Produit non trouvé avec l'ID: 999"
}
```

## Configuration

### Ports personnalisés

Pour changer le port par défaut (8080), modifier `application.properties` :

```properties
server.port=8081
```

### Configuration Swagger

La configuration Swagger peut être personnalisée dans `application.properties` :

```properties
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
```
