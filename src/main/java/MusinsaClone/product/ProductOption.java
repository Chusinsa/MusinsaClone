package MusinsaClone.product;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionName;  // 예: 색상

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "parent_option_id")
    private ProductOption parentOption;

    @OneToMany(mappedBy = "parentOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> subOptions = new ArrayList<>();

    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionSub> optionValues = new ArrayList<>();

    public ProductOption() {
    }

    public ProductOption(String optionName, Product product) {
        this.optionName = optionName;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getOptionName() {
        return optionName;
    }

    public Product getProduct() {
        return product;
    }

    public ProductOption getParentOption() {
        return parentOption;
    }

    public List<ProductOption> getSubOptions() {
        return subOptions;
    }

    public List<ProductOptionSub> getOptionValues() {
        return optionValues;
    }

    public void setParentOption(ProductOption parentOption) {
        this.parentOption = parentOption;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void addSubOption(ProductOption productOption) {
        productOption.setParentOption(this);
        this.subOptions.add(productOption);
    }

    public void addOptionValue(ProductOptionSub optionSub) {
        optionSub.setOptionGroup(this);
        this.optionValues.add(optionSub);
    }
}
