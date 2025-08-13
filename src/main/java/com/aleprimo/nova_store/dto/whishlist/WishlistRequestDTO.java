package com.aleprimo.nova_store.dto.whishlist;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear o actualizar una lista de deseos (wishlist)")
public class WishlistRequestDTO {

    @Schema(description = "Nombre de la lista de deseos", example = "Mis favoritos")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "ID del cliente propietario de la lista", example = "5")
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    @Schema(description = "Lista de IDs de productos incluidos", example = "[1, 2, 3]")
    @NotNull(message = "Product IDs cannot be null")
    private List<Long> productIds;
}
