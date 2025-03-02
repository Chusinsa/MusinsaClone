package MusinsaClone.order;

import MusinsaClone.customers.Customer;
import MusinsaClone.customers.LoginCustomerResolver;
import MusinsaClone.order.DTO.CreateOrderRequest;
import MusinsaClone.order.DTO.OrderViewResponse;
import MusinsaClone.order.DTO.OrderListResponse;
import MusinsaClone.order.DTO.OrderResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderRestController {

    private final OrderService orderService;
    private final LoginCustomerResolver loginCustomerResolver;

    public OrderRestController(OrderService orderService, LoginCustomerResolver loginCustomerResolver) {
        this.orderService = orderService;
        this.loginCustomerResolver = loginCustomerResolver;
    }

    @PostMapping("/orders")
    public OrderResponse create(@RequestBody CreateOrderRequest createOrderRequest,
                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Customer customer = loginCustomerResolver.resolveCustomerFromToken(token);
        return orderService.create(createOrderRequest, customer);
    }

    @GetMapping("/orders")
    public OrderListResponse getAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Customer customer = loginCustomerResolver.resolveCustomerFromToken(token);
        return orderService.getAll(customer);
    }

    @GetMapping("/orders/{orderId}")
    public OrderViewResponse getDetail(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                       @PathVariable Long orderId) {
        Customer customer = loginCustomerResolver.resolveCustomerFromToken(token);
        return orderService.getDetail(orderId, customer);
    }

    @DeleteMapping("/orders/{orderId}")
    public void delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                       @PathVariable Long orderId) {
        Customer customer = loginCustomerResolver.resolveCustomerFromToken(token);
        orderService.delete(orderId, customer);
    }
}
