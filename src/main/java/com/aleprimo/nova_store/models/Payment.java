package com.aleprimo.nova_store.models;


import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false , name = "payment_method")
    PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_status")
    PaymentStatus paymentStatus;

    @Column(nullable = false)
    BigDecimal amount;

    @CreationTimestamp
     LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
