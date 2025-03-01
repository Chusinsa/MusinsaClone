package MusinsaClone.review;

import MusinsaClone.customers.Customer;
import MusinsaClone.customers.LoginCustomerResolver;
import MusinsaClone.review.dto.ReviewCreate;
import MusinsaClone.review.dto.ReviewCreateResponse;
import MusinsaClone.review.dto.ReviewResponse;
import MusinsaClone.review.dto.ReviewUpdateDTO;
import MusinsaClone.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewRestController {

    private final ReviewService reviewService;
    private final LoginCustomerResolver loginCustomerResolver;

    public ReviewRestController(ReviewService reviewService, LoginCustomerResolver loginCustomerResolver) {
        this.reviewService = reviewService;
        this.loginCustomerResolver = loginCustomerResolver;
    }

    @PostMapping("/reviews")
    public ReviewCreateResponse create(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody ReviewCreate reviewCreate) {
        Customer customer = loginCustomerResolver.resolveCustomerFromToken(token);
        return reviewService.create(reviewCreate, customer);
    }

    @PutMapping("/reviews")
    public ReviewResponse update(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody ReviewUpdateDTO reviewUpdate) {
        Customer customer = loginCustomerResolver.resolveCustomerFromToken(token);
        return reviewService.update(customer, reviewUpdate);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ApiResponse<Void> delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, Long reviewId) {
        Customer customer = loginCustomerResolver.resolveCustomerFromToken(token);
        reviewService.delete(customer, reviewId);
        return ApiResponse.success(null);
    }
}
