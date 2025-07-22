package com.aleprimo.nova_store.models;

import com.aleprimo.nova_store.models.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    Order order;

    @Column(nullable = false)
    BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_method")
    PaymentMethod paymentMethod;

    @Column(nullable = false, name = "issued_at")
    LocalDateTime issuedAt;

    @PrePersist
    public void prePersist() {
        this.issuedAt = LocalDateTime.now();
    }
}
