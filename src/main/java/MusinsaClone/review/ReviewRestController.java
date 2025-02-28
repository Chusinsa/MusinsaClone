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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
}
