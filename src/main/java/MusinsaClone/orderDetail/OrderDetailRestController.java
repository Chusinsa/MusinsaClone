package MusinsaClone.orderDetail;

import MusinsaClone.order.DTO.CreateOrderRequest;
import MusinsaClone.orderDetail.DTO.CreateOrderDetailResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderDetailRestController {

    private final OrderDetailService orderDetailService;

    public OrderDetailRestController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping("/orderdetails")
    public CreateOrderDetailResponse create(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderDetailService.create(createOrderRequest);
    }
}
