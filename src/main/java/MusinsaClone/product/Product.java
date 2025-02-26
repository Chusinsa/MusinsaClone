package MusinsaClone.product;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private String description;

    private Category category;

    private Condition productCondition;

    private boolean isDeleted = false;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> productOptions = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int reviewCount = 0;

    private boolean isOnSale = true;

    private boolean isPrivate = false;

    public Product() {
    }

    public Product(String name, int price, String description, Category category, Condition productCondition,LocalDateTime createdAt) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.productCondition = productCondition;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public Condition getProductCondition() {
        return productCondition;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public List<ProductOption> getProductOptions() {
        return productOptions;
    }

    public void addProductOptions(List<ProductOption> productOptions) {
        this.productOptions.addAll(productOptions);
    }
}
