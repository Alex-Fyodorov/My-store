package ru.gb.my.market.core;

import ru.gb.my.market.api.dtos.CategoryDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FullServerRunTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void fullRestTest() {
        List<CategoryDto> categoryDtoList = testRestTemplate.getForObject(
                "/api/v1/categories", List.class);
        Assertions.assertThat(categoryDtoList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(4);
    }
}
