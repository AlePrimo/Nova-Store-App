package com.aleprimo.nova_store.dto.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta con información de un cupón")
public class CouponResponseDTO {
    @Schema(description = "ID único del cupón", example = "1")
    private Long id;
    @Schema(description = "Código único del cupón", example = "DESCUENTO10")
    private String code;
    @Schema(description = "Porcentaje de descuento del cupón", example = "10")
    private Double discountPercentage;
    @Schema(description = "Fecha de expiración del cupón", example = "2025-12-31T23:59:59")
    private LocalDateTime expirationDate;
    @Schema(description = "Indica si el cupón está activo", example = "true")
    private Boolean isActive;
}