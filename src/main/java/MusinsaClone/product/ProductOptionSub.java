package MusinsaClone.product;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ProductOptionSub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionValue;  // 예: 230, 240

    private Integer stock;  // 재고 수량

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private ProductOption optionGroup;

    public ProductOptionSub() {
    }

    public ProductOptionSub(String optionValue, Integer stock) {
        this.optionValue = optionValue;
        this.stock = stock;
    }

    public ProductOptionSub(String optionValue, Integer stock, ProductOption optionGroup) {
        this.optionValue = optionValue;
        this.stock = stock;
        this.optionGroup = optionGroup;
    }

    public Long getId() {
        return id;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public Integer getStock() {
        return stock;
    }

    public ProductOption getOptionGroup() {
        return optionGroup;
    }

    public void setOptionGroup(ProductOption optionGroup) {
        this.optionGroup = optionGroup;
    }
}
