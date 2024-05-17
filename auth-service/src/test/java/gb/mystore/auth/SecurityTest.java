package gb.mystore.auth;

import gb.mystore.api.dtos.JwtRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void securityAccessAllowedTest() throws Exception{
        testRestTemplate.getRestTemplate()
                .setInterceptors(Collections.singletonList(((request, body, execution) -> {
                    request.getHeaders()
                            .add("username", "user1");
                    return execution.execute(request, body);
                })));
        String username = testRestTemplate.getForObject("/api/v1/users", String.class);
        System.out.println("==========================================");
        System.out.println(username);
        System.out.println("==========================================");
        Assertions.assertThat(username).isNotNull();
    }

    @Test
    public void securityTokenTest() throws Exception{
        JwtRequest request = new JwtRequest();
        request.setUsername("user1");
        request.setPassword("100");
        String token = testRestTemplate.postForObject(
                "/authenticate", request, String.class);
        System.out.println("==========================================");
        System.out.println(token);
        System.out.println("==========================================");
        Assertions.assertThat(token).isNotNull();
    }
}
