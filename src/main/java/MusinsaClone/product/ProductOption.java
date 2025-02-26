package MusinsaClone.product;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private String optionName;

    @OneToMany(mappedBy = "productOption")
    private List<ProductOptionDetail> optionDetails = new ArrayList<>();

    public ProductOption() {
    }

    public ProductOption(Product product,String optionName, List<ProductOptionDetail> optionDetails) {
        this.product = product;
        this.optionName = optionName;
        this.optionDetails = optionDetails;
    }

    public ProductOption(Product product, String optionName) {
        this.product = product;
        this.optionName = optionName;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getOptionName() {
        return optionName;
    }

    public List<ProductOptionDetail> getOptionDetails() {
        return optionDetails;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOptionDetails(List<ProductOptionDetail> optionDetails) {
        this.optionDetails = optionDetails;
    }
}
