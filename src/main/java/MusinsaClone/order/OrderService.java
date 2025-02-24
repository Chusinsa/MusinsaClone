package MusinsaClone.order;

import MusinsaClone.order.DTO.CreateOrderRequest;
import MusinsaClone.order.DTO.OrderListResponse;
import MusinsaClone.order.DTO.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public OrderResponse create(CreateOrderRequest createOrderRequest, Customer customer) {
        Order order = new Order(customer, createOrderRequest.address());
        orderRepository.save(order);
        return new OrderResponse(
                order.getId(),
                order.getAddress(),
                order.getCreatedAt()
        );
    }

    public OrderListResponse getAll(Customer customer) {
        List<Order> orders = orderRepository.findByCustomer(customer);
        return new OrderListResponse(
                orders.stream()
                        .map(order -> new OrderListResponse.OrderInfo(order.getId(), order.getCustomer().getName))
                        .toList()
        );
    }
}
