package com.aleprimo.nova_store.dto.whishlist;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponseDTO {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private Long customerId;
    private List<Long> productIds;
}
