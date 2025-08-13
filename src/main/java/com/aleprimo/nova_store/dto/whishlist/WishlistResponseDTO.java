package com.aleprimo.nova_store.dto.whishlist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta con información de una lista de deseos")
public class WishlistResponseDTO {
    @Schema(description = "ID único de la lista de deseos", example = "1")
    private Long id;
    @Schema(description = "Nombre de la lista de deseos", example = "Mis favoritos")
    private String name;
    @Schema(description = "Fecha de creación de la lista", example = "2025-08-12T10:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "ID del cliente propietario de la lista", example = "5")
    private Long customerId;
    @Schema(description = "Lista de IDs de productos incluidos", example = "[1, 2, 3]")
    private List<Long> productIds;
}
