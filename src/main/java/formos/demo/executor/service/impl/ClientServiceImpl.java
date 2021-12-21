package formos.demo.executor.service.impl;

import formos.demo.executor.domain.Client;
import formos.demo.executor.domain.Order;
import formos.demo.executor.domain.OrderItem;
import formos.demo.executor.dto.OrderDTO;
import formos.demo.executor.repository.ClientRepository;
import formos.demo.executor.repository.OrderItemRepository;
import formos.demo.executor.repository.OrderRepository;
import formos.demo.executor.service.ClientService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public ClientServiceImpl(ClientRepository clientRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.clientRepository = clientRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> partialUpdate(Client client) {
        log.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                return existingClient;
            })
            .map(clientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        log.debug("Request to get all Clients");
        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteClientInMenu(Long id) {
        Optional<Client> client = this.clientRepository.findById(id);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        if (client.isPresent()) {
            for (Order order : client.get().getOrders()) {
                Order orderById = this.orderRepository.getById(order.getId());
                orderDTOS.add(orderToOrderDTO(orderById));
            }
        }
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
}
