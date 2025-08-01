package com.aleprimo.nova_store.dto.whishlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Product IDs cannot be null")
    private List<Long> productIds;
}
