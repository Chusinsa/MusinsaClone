package MusinsaClone.orderDetail;

import MusinsaClone.order.DTO.CreateOrderRequest;
import MusinsaClone.order.Order;
import MusinsaClone.order.OrderRepository;
import MusinsaClone.orderDetail.DTO.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository,
                              OrderRepository orderRepository,
                              ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CreateOrderDetailResponse create(CreateOrderDetailRequest createOrderDetailRequest) {
        Order order = orderRepository.findById(createOrderDetailRequest.orderId()).orElseThrow(
                () -> new NoSuchElementException("해당하는 주문이 없습니다."));
        Product product = productRepository.findById(createOrderDetailRequest.productId).orElseThrow(
                () -> new NoSuchElementException("해당하는 상품이 없습니다."));
        OrderDetail orderDetail = orderDetailRepository.save(
                new OrderDetail(
                        order,
                        product,
                        createOrderDetailRequest.productCount(),
                        createOrderDetailRequest.price()));
        order.updateTotalPrice(createOrderDetailRequest.price() * createOrderDetailRequest.productCount());
        return new CreateOrderDetailResponse(orderDetail.getId(), orderDetail.getProduct().getId);
    }

    public OrderDetailListResponse getAll(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_Id(orderId);
        return new OrderDetailListResponse(
                orderId,
                orderDetails
                        .stream()
                        .map(orderDetail -> new OrderDetailResponse(
                                orderDetail.getId(),
                                orderDetail.getProduct().getId))
                        .toList()
        );
    }

    public OrderDetailviewResponse getDetail(Long orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(
                () -> new NoSuchElementException("해당하는 상세주문이 없습니다."));
        return new OrderDetailviewResponse(
                orderDetail.getId(),
                orderDetail.getOrder().getId(),
                orderDetail.getProduct().getId,
                orderDetail.getProductCount(),
                orderDetail.getPrice());
    }
}
