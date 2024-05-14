package gb.mystore.core.configs;

import gb.mystore.core.properties.CartServiceIntegrationProperties;
import gb.mystore.core.properties.UserServiceIntegrationProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({CartServiceIntegrationProperties.class,
        UserServiceIntegrationProperties.class})
public class CoreAppConfig {
    private final CartServiceIntegrationProperties cartServiceIntegrationProperties;
    private final UserServiceIntegrationProperties userServiceIntegrationProperties;

    @Bean
    public WebClient cartServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        cartServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(
                            cartServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(
                            cartServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
                });
        return WebClient
                .builder()
                .baseUrl(cartServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

    @Bean
    public WebClient userServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        userServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(
                            userServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(
                            userServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
                });
        return WebClient
                .builder()
                .baseUrl(userServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
