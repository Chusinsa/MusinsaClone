package MusinsaClone.product;


import jakarta.persistence.*;

@Entity
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    private String size;

    private int stock;

    private boolean isDeleted = false;

    @ManyToOne
    private Product product;


    protected ProductOption() {
    }

    public ProductOption(String color, String size, int stock, Product product) {
        this.color = color;
        this.size = size;
        this.stock = stock;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public int getStock() {
        return stock;
    }

    public Product getProduct() {
        return product;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(){
        this.isDeleted=true;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
