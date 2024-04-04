package gb.mystore.repositories;

import gb.mystore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
    Page<Product> findAllByPriceBetween(Integer min, Integer max, Pageable pageable);

    @Query("select p from Product p where p.price >= :min and p.price <= :max")
    List<Product> findAllWithPriceFilter(Integer min, Integer max);

    @Transactional
    @Modifying
    @Query("update Product p set p.price = :newPrice where p.id = :id")
    void changePrice(Long id, BigDecimal newPrice);
}
