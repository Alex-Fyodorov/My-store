package gb.mystore.core;

import gb.mystore.core.entities.Category;
import gb.mystore.core.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findAllTest() {
        Category category = new Category();
        category.setTitle("Any");
        category.setProducts(Collections.emptyList());
        testEntityManager.persist(category);
        testEntityManager.flush();

        List<Category> categories = (List<Category>) categoryRepository.findAll();
        System.out.println("===================================");
        Assertions.assertEquals(5, categories.size());
        Assertions.assertEquals("Fish", categories.get(3).getTitle());
    }
}
