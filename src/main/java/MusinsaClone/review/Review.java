package MusinsaClone.review;

import MusinsaClone.customers.Customer;
import MusinsaClone.product.Product;
import MusinsaClone.util.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Review extends BaseEntity {


    // Product 필드에 reviewCount 추가하고 싶다... 민영님..

    // LoginCustomerResolver 에서 레포지토리에게 findById를 하도록 바로 시킴 -> 못찾을 경우 null을 반환 -> 예외처리 없이 널포인터익셉션 터짐
    // customer 로그인할때 request로 받는 요청이 너무 많음 id랑 password만 줘도 됨
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

    public void updateReview(String title, String detail) {
        if (title != null) {
            this.title = title;
        }
        if (detail != null) {
            this.detail = detail;
        }
    }

    public boolean isCustomerMatch(Customer customer) {  // 자신이 쓴 리뷰가 맞는지 검증하는 함수
        if (this.customer.equals(customer)) {
            return true;
        } else throw new IllegalArgumentException("회원이 일치하지 않습니다.");
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
