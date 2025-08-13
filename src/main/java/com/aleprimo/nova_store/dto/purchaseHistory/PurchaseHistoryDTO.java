package com.aleprimo.nova_store.dto.purchaseHistory;

import com.aleprimo.nova_store.models.enums.OrderStatus;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.ShippingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "Historial de compras de un cliente")
public class PurchaseHistoryDTO {

    @Schema(description = "ID único del pedido", example = "101")
    private Long orderId;
    @Schema(description = "Fecha en que se realizó el pedido", example = "2025-08-12T14:30:00")
    private LocalDateTime orderDate;
    @Schema(description = "Monto total del pedido", example = "3500.00")
    private BigDecimal totalAmount;
    @Schema(description = "Método de pago utilizado", example = "DEBIT_CARD")
    private PaymentMethod paymentMethod;
    @Schema(description = "Estado actual del pedido", example = "SHIPPED")
    private OrderStatus orderStatus;
    @Schema(description = "Lista de ítems incluidos en el pedido")
    private List<OrderItemDTO> items;
    @Schema(description = "Dirección de envío", example = "Av. Siempre Viva 742")
    private String shippingAddress;
    @Schema(description = "Ciudad de envío", example = "Springfield")
    private String shippingCity;
    @Schema(description = "Código postal del envío", example = "12345")
    private String shippingPostalCode;
    @Schema(description = "País de envío", example = "USA")
    private String shippingCountry;
    @Schema(description = "Estado del envío", example = "DELIVERED")
    private ShippingStatus shippingStatus;
    @Schema(description = "Fecha de envío", example = "2025-08-15T10:00:00")
    private LocalDateTime shippedAt;
    @Schema(description = "Fecha de entrega", example = "2025-08-17T16:00:00")
    private LocalDateTime deliveredAt;
}
