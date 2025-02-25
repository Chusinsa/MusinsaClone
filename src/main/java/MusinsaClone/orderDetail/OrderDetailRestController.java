package MusinsaClone.orderDetail;

import MusinsaClone.order.DTO.CreateOrderRequest;
import MusinsaClone.orderDetail.DTO.CreateOrderDetailRequest;
import MusinsaClone.orderDetail.DTO.CreateOrderDetailResponse;
import MusinsaClone.orderDetail.DTO.OrderDetailListResponse;
import MusinsaClone.orderDetail.DTO.OrderDetailviewResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderDetailRestController {

    private final OrderDetailService orderDetailService;

    public OrderDetailRestController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping("/orderdetails")
    public CreateOrderDetailResponse create(@RequestBody CreateOrderDetailRequest createOrderDetailRequest) {
        return orderDetailService.create(createOrderDetailRequest);
    }

    @GetMapping("/orderdetails/{orderId}")
    public OrderDetailListResponse getAll(@PathVariable Long orderId) {
        return orderDetailService.getAll(orderId);
    }

    @GetMapping("/orderdetails/{orderDetailId}")
    public OrderDetailviewResponse getDetail(@PathVariable Long orderDetailId) {
        return orderDetailService.getDetail(orderDetailId);
    }
}
