package com.aleprimo.nova_store.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    Customer customer;
    @Column(name = "order_items")
    List<OrderItem> orderItems = new ArrayList<>();
    @Column(name = "total_amount")
    Double totalAmount;





}
