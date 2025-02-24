package MusinsaClone.order;

import MusinsaClone.order.DTO.CreateOrderRequest;
import MusinsaClone.order.DTO.OrderListResponse;
import MusinsaClone.order.DTO.OrderResponse;
import MusinsaClone.util.LoginMemberResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    private final OrderService orderService;
    private final LoginMemberResolver loginMemberResolver;

    public OrderRestController(OrderService orderService, LoginMemberResolver loginMemberResolver) {
        this.orderService = orderService;
        this.loginMemberResolver = loginMemberResolver;
    }

    public OrderResponse create(@RequestBody CreateOrderRequest createOrderRequest,
                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Customer customer = loginMemberResolver.resolveCustomerFromToken(token);
        return orderService.create(customer, createOrderRequest);
    }

    public OrderListResponse getAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Customer customer = loginMemberResolver.resolveCustomerFromToken(token);
        return orderService.getAll(customer);
    }
}
