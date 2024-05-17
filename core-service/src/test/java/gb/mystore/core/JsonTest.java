package gb.mystore.core;

import gb.mystore.core.entities.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.util.Collections;


@org.springframework.boot.test.autoconfigure.json.JsonTest
public class JsonTest {
    @Autowired
    private JacksonTester<Category> jacksonTester;

    @Test
    public void jsonSerializationTest() throws IOException {
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Any");
        category.setProducts(Collections.emptyList());

        Assertions.assertThat(jacksonTester.write(category))
                .hasJsonPathNumberValue("$.id")
                .extractingJsonPathStringValue("$.title")
                .isEqualTo("Any");
    }

    @Test
    public void jsonDeserializationTest() throws Exception {
        String content = "{\"id\": 1, \"title\": \"Any\", \"products\": []}";
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Any");
        category.setProducts(Collections.emptyList());

        Assertions.assertThat(jacksonTester.parse(content)).isEqualTo(category);
        Assertions.assertThat(jacksonTester.parseObject(content).getTitle())
                .isEqualTo("Any");
    }
}
