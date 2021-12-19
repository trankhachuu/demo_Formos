package formos.demo.executor.service;

import formos.demo.executor.domain.*;
import formos.demo.executor.dto.MenuDataRequestDTO;
import formos.demo.executor.dto.SaveMenuDataRequestDTO;
import formos.demo.executor.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ClientRepository clientRepository;
    private final BeerRepository beerRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        OrderItemRepository orderItemRepository,
                        ClientRepository clientRepository,
                        BeerRepository beerRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.clientRepository = clientRepository;
        this.beerRepository = beerRepository;
    }

    @Transactional
    public void saveOrderData(@RequestBody SaveMenuDataRequestDTO request) {
        List<OrderItem> orderItems = new ArrayList<>();
        Set<Order> orderList = new HashSet<>();
        Client client = new Client();
        Optional<User> user = this.userRepository.findOneByLogin(request.getDataRequests().get(0).getUsername());
        for (int i = 0; i < request.getDataRequests().size(); i++) {
            // save at table order
            Order tempOrder= requestToOrder(request.getDataRequests().get(i));
            Order order = this.orderRepository.save(tempOrder);
            orderList.add(order);

            Optional<Beer> beer = this.beerRepository.findById(request.getDataRequests().get(i).getId());
            if (beer.isPresent()) {
                orderItems.add(requestToOrderItem(beer.get(), order));
            }
        }
        client.setOrders(orderList);
        client.setUser(user.get());
        this.clientRepository.save(client);
        this.orderItemRepository.saveAll(orderItems);
    }

    private Order requestToOrder(MenuDataRequestDTO request) {
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setShipDate(LocalDate.now());
        order.setDisCount(0);
        order.setTotal(Double.valueOf(request.getTotal()));
        order.setStautus(false);
        order.setPrice(Double.valueOf(request.getPrice()));
        order.setQuantity(request.getQuantity());
        return order;
    }

    private OrderItem requestToOrderItem(Beer beer, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBeer(beer);
        orderItem.setOrderId(order);
        return orderItem;
    }
}

