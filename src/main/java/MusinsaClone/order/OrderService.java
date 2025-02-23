package MusinsaClone.order;

import MusinsaClone.order.DTO.CreateOrderRequest;
import MusinsaClone.order.DTO.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public OrderResponse create(CreateOrderRequest createOrderRequest) {
        Customer customer = customerRepository.findById(createOrderRequest.customerId());
        Order order = new Order(customer, createOrderRequest.address());
        orderRepository.save(order);
        return new OrderResponse(
                order.getId(),
                order.getAddress(),
                order.getCreatedAt()
        );
    }

    public
}
