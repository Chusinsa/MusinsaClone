package MusinsaClone.order;

import MusinsaClone.util.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    private String address;

    private int totalPrice = 0;

    public Order(Customer customer, String address) {
        this.customer = customer;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getAddress() {
        return address;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
