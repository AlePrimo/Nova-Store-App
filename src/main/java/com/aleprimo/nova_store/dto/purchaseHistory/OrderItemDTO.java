package com.aleprimo.nova_store.dto.purchaseHistory;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDTO {
    private String productName;
    private int quantity;
    private BigDecimal price;
}
