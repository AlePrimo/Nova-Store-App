package com.aleprimo.nova_store.dto.purchaseHistory;

import com.aleprimo.nova_store.models.enums.OrderStatus;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.ShippingStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PurchaseHistoryDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;

    private List<OrderItemDTO> items;

    private String shippingAddress;
    private String shippingCity;
    private String shippingPostalCode;
    private String shippingCountry;
    private ShippingStatus shippingStatus;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
}
