package MusinsaClone.review;

import MusinsaClone.customers.Customer;
import MusinsaClone.product.Product;
import MusinsaClone.util.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Review extends BaseEntity {


    // Product 필드에 reviewCount 추가하고 싶다... 민영님..

    // LoginCustomerResolver 에서 레포지토리에게 findById를 하도록 바로 시킴 -> 못찾을 경우 null을 반환 -> 예외처리 없이 널포인터익셉션 터짐
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

    private String title;

    private String detail;

    public Review(Customer customer, Product product, String title, String detail) {
        this.customer = customer;
        this.product = product;
        this.title = title;
        this.detail = detail;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }
}
