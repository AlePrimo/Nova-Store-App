package com.aleprimo.nova_store.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    Customer customer;
    @Column(name = "order_items")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItems = new ArrayList<>();
    @Column(name = "total_amount", nullable = false)
    BigDecimal totalAmount;
    @Column(nullable = false)
    String paymentMethod;
    @Column(nullable = false)
    String status;
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;
    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
