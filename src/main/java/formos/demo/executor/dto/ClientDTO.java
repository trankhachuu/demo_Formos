package formos.demo.executor.dto;

import formos.demo.executor.domain.User;
import java.util.HashSet;
import java.util.Set;

public class ClientDTO {

    private Long id;
    private User user;
    private Set<OrderDTO> orders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderDTO> orders) {
        this.orders = orders;
    }
}
