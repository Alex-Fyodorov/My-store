package gb.mystore.repositories.specifications;

import gb.mystore.entities.Category;
import gb.mystore.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> priceGreaterOrEqualThan(BigDecimal price) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price));
    }

    public static Specification<Product> priceLessOrEqualThan(BigDecimal price) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), price));
    }

    public static Specification<Product> likeOf(String titlePart) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart)));
    }

    public static Specification<Product> categoryEqualThan(Category category) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category"), category));
    }
}
