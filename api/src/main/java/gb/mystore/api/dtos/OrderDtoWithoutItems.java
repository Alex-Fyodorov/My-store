package gb.mystore.api.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDtoWithoutItems {

    private Long id;
    private String username;
    private BigDecimal totalPrice;
    private String createdAt;

    public OrderDtoWithoutItems() {
    }

    public OrderDtoWithoutItems(Long id, String username,
                                BigDecimal totalPrice, String createdAt) {
        this.id = id;
        this.username = username;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
