package MusinsaClone.review;

import MusinsaClone.customers.Customer;
import MusinsaClone.customers.CustomerRepository;
import MusinsaClone.product.Product;
import MusinsaClone.product.ProductRepository;
import MusinsaClone.review.DTO.ReviewCreate;
import MusinsaClone.review.DTO.ReviewCreateResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public ReviewCreateResponse create(ReviewCreate dto, Customer customer) {
        Product product = productRepository.findById(dto.productId()).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 상품 id" + dto.productId()));

        Customer loginCustomer = customerRepository.findByLoginId(customer.getLoginId());

        Review review = new Review(loginCustomer, product, dto.title(), dto.detail());
        reviewRepository.save(review);
        return new ReviewCreateResponse(review.getId(), review.getCreatedAt());
    }

    @Transactional
    public void delete(Customer customer, Long reviewId) {
        Customer loginCustomer = customerRepository.findByLoginId(customer.getLoginId());

        reviewRepository.deleteById(reviewId);
    }
}
