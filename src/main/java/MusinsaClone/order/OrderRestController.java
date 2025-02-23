package MusinsaClone.order;

import MusinsaClone.order.DTO.CreateOrderRequest;
import MusinsaClone.order.DTO.OrderResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderResponse create(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.create(createOrderRequest);
    }
}
