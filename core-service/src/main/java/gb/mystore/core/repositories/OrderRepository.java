package gb.mystore.core.repositories;

import gb.mystore.core.entities.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Transactional
    @Query("select o from Order o where o.username = :username")
    List<Order> findByUserName(String username);
}
