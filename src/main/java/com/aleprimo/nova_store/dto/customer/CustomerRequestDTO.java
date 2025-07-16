package com.aleprimo.nova_store.dto.customer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isActive;
}
