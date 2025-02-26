package MusinsaClone.product;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ProductOptionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProductOption productOption;

    private String optionValue;

    private int stock;

    public ProductOptionDetail() {
    }

    public ProductOptionDetail(ProductOption productOption, String optionValue, int stock) {
        this.productOption = productOption;
        this.optionValue = optionValue;
        this.stock = stock;
    }



    public Long getId() {
        return id;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public int getStock() {
        return stock;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }
}
