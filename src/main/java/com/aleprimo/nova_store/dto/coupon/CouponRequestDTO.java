package com.aleprimo.nova_store.dto.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Petición para crear o actualizar un cupón")
public class CouponRequestDTO {
    @Schema(description = "Código único del cupón", example = "DESCUENTO10")
    @NotBlank
    private String code;
    @Schema(description = "Porcentaje de descuento (0 a 100)", example = "15")
    @NotNull
    @Min(0)
    @Max(100)
    private Double discountPercentage;
    @Schema(description = "Fecha de expiración del cupón", example = "2025-12-31T23:59:59")
    @NotNull
    @Future
    private LocalDateTime expirationDate;
    @Schema(description = "Indica si el cupón está activo", example = "true")
    @NotNull
    private Boolean isActive;


}