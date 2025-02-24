package MusinsaClone.order;

import MusinsaClone.order.DTO.CreateOrderRequest;
import MusinsaClone.order.DTO.OrderViewResponse;
import MusinsaClone.order.DTO.OrderListResponse;
import MusinsaClone.order.DTO.OrderResponse;
import MusinsaClone.util.LoginMemberResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderRestController {

    private final OrderService orderService;
    private final LoginMemberResolver loginMemberResolver;

    public OrderRestController(OrderService orderService, LoginMemberResolver loginMemberResolver) {
        this.orderService = orderService;
        this.loginMemberResolver = loginMemberResolver;
    }

    @PostMapping("/orders")
    public OrderResponse create(@RequestBody CreateOrderRequest createOrderRequest,
                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Customer customer = loginMemberResolver.resolveCustomerFromToken(token);
        return orderService.create(createOrderRequest, customer);
    }

    @GetMapping("/orders")
    public OrderListResponse getAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Customer customer = loginMemberResolver.resolveCustomerFromToken(token);
        return orderService.getAll(customer);
    }

    @GetMapping("/orders/{orderId}")
    public OrderViewResponse getDetail(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                       @PathVariable Long orderId) {
        Customer customer = loginMemberResolver.resolveCustomerFromToken(token);
        return orderService.getDetail(orderId);
    }
}
