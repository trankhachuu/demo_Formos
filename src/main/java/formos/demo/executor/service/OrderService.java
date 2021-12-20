package formos.demo.executor.service;

import formos.demo.executor.domain.*;
import formos.demo.executor.dto.ClientDTO;
import formos.demo.executor.dto.MenuDataRequestDTO;
import formos.demo.executor.dto.OrderDTO;
import formos.demo.executor.dto.SaveMenuDataRequestDTO;
import formos.demo.executor.repository.*;
import java.time.LocalDate;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ClientRepository clientRepository;
    private final BeerRepository beerRepository;

    public OrderService(
        OrderRepository orderRepository,
        UserRepository userRepository,
        OrderItemRepository orderItemRepository,
        ClientRepository clientRepository,
        BeerRepository beerRepository
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.clientRepository = clientRepository;
        this.beerRepository = beerRepository;
    }

    @Transactional
    public void saveOrderData(@RequestBody SaveMenuDataRequestDTO request) {
        if (request.getDataRequests() != null && request.getDataRequests().size() > 0) {
            List<OrderItem> orderItems = new ArrayList<>();
            Set<Order> orderList = new HashSet<>();
            Client client = new Client();
            Optional<User> user = this.userRepository.findOneByLogin(request.getDataRequests().get(0).getUsername());
            for (int i = 0; i < request.getDataRequests().size(); i++) {
                // save at table order
                Order tempOrder = requestToOrder(request.getDataRequests().get(i));
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
    }

    private Long checkExitClient(User user) {
        if (user.getId() != null) {
            Client client = this.clientRepository.getClientByUserId(user.getId());
            if (client.getId() != null) {
                return client.getId();
            }
        }
        return null;
    }

    public ClientDTO getClient(String username) {
        Optional<User> user = this.userRepository.findOneByLogin(username);
        Client client = new Client();
        ClientDTO clientDTO = new ClientDTO();
        if (user.isPresent()) {
            client = this.clientRepository.getClientByUserId(user.get().getId());
            if (client == null) {
                return new ClientDTO();
            }
            clientDTO.setId(client.getId());
            Set<OrderDTO> orderDTOS = new HashSet<>();
            for (Order order : client.getOrders()) {
                Order orderById = this.orderRepository.getById(order.getId());
                orderDTOS.add(orderToOrderDTO(orderById));
            }
            clientDTO.setOrders(orderDTOS);
            clientDTO.setUser(client.getUser());
        }
        return clientDTO;
    }

    private OrderDTO orderToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setShipDate(order.getShipDate());
        orderDTO.setDisCount(order.getDisCount());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setStautus(order.getStautus());
        orderDTO.setOrderItems(order.getOrderItems());
        for (OrderItem orderItem : order.getOrderItems()) {
            orderDTO.setImage(orderItem.getBeer().getImage());
            orderDTO.setBeerId(orderItem.getBeer().getId());
            orderDTO.setManufacturer(orderItem.getBeer().getManufacturer());
            orderDTO.setBeerName(orderItem.getBeer().getName());
            orderDTO.setDescription(orderItem.getBeer().getDescription());
        }
        orderDTO.setClient(order.getClient());
        return orderDTO;
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
