package MusinsaClone.product;

import MusinsaClone.util.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Condition productCondition;

    private boolean isDeleted = false;

    private int reviewCount = 0;

    private boolean isOnSale = true;

    private boolean isPrivate = false;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> options = new ArrayList<>();

    public Product() {
    }

    public Product(String name, int price, String description, Category category, Condition productCondition) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.productCondition = productCondition;
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


    public int getReviewCount() {
        return reviewCount;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public List<ProductOption> getOptions() {
        return options;
    }

    public void setDeleted(){
        this.isDeleted=true;
    }

    public void addOption(ProductOption productOption) {
        this.options.add(productOption);
        productOption.setProduct(this);
    }

    public void update(String name,
                       int price,
                       String description,
                       Condition productCondition,
                       List<ProductOption> options){
        this.name = name;
        this.price = price;
        this.description = description;
        this.productCondition = productCondition;
        this.options = options;
    }
}
