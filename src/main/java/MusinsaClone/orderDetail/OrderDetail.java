package MusinsaClone.orderDetail;

import MusinsaClone.order.Order;
import MusinsaClone.product.Product;
import jakarta.persistence.*;

@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    private int productCount;

    private int price;

    public OrderDetail(Order order, Product product, int productCount, int price) {
        this.order = order;
        this.product = product;
        this.productCount = productCount;
        this.price = price;
    }

    protected OrderDetail() {
    }

    public Long getId() {
        return Id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public int getProductCount() {
        return productCount;
    }

    public int getPrice() {
        return price;
    }
}
