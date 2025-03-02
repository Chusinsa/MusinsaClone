package MusinsaClone.order;

import MusinsaClone.customers.Customer;
import MusinsaClone.customers.CustomerRepository;
import MusinsaClone.order.DTO.CreateOrderRequest;
import MusinsaClone.order.DTO.OrderViewResponse;
import MusinsaClone.order.DTO.OrderListResponse;
import MusinsaClone.order.DTO.OrderResponse;
import MusinsaClone.orderDetail.OrderDetail;
import MusinsaClone.orderDetail.OrderDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public OrderResponse create(CreateOrderRequest createOrderRequest, Customer customer) {
        customerRepository.findById(customer.getId()).orElseThrow(
                () -> new NoSuchElementException("해당하는 고객이 없습니다."));
        Order order = new Order(customer, createOrderRequest.address());
        orderRepository.save(order);
        return new OrderResponse(
                order.getId(),
                order.getAddress(),
                order.getCreatedAt()
        );
    }

    public OrderListResponse getAll(Customer customer) {
        customerRepository.findById(customer.getId()).orElseThrow(
                () -> new NoSuchElementException("해당하는 고객이 없습니다."));
        List<Order> orders = orderRepository.findByCustomer(customer);
        return new OrderListResponse(
                orders.stream()
                        .map(order -> new OrderListResponse.OrderInfo(order.getId(), order.getCustomer().getUsername()))
                        .toList()
        );
    }

    public OrderViewResponse getDetail(Long orderId, Customer customer) {
        customerRepository.findById(customer.getId()).orElseThrow(
                () -> new NoSuchElementException("해당하는 고객이 없습니다."));
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NoSuchElementException("해당하는 주문이 없습니다."));
        return new OrderViewResponse(
                order.getId(),
                order.getCustomer().getId(),
                order.getAddress(),
                order.getTotalPrice());
    }

    @Transactional
    public void delete(Long orderId, Customer customer) {
        customerRepository.findById(customer.getId()).orElseThrow(
                () -> new NoSuchElementException("해당하는 고객이 없습니다."));
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_Id(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailRepository.delete(orderDetail);
        }
        orderRepository.deleteById(orderId);
    }
}
