package MusinsaClone.review;

import MusinsaClone.customers.Customer;
import MusinsaClone.customers.LoginCustomerResolver;
import MusinsaClone.review.DTO.ReviewCreate;
import MusinsaClone.review.DTO.ReviewCreateResponse;
import MusinsaClone.util.ApiResponse;
import MusinsaClone.util.JwtProvider;
import MusinsaClone.util.LoginAdminResolver;
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

    @DeleteMapping("/reviews/{reviewId}")
    public ApiResponse<Void> delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, Long reviewId) {
        Customer customer = loginCustomerResolver.resolveCustomerFromToken(token);
        reviewService.delete(customer, reviewId);
        return ApiResponse.success(null);
    }
}
