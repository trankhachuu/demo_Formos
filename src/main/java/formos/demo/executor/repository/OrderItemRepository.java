package formos.demo.executor.repository;

import formos.demo.executor.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "DELETE From order_item WHERE order_id_id=:orderId", nativeQuery = true)
    void deleteOrderItemByOrderId(@Param("orderId") Long orderId);
}
