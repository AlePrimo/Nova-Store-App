package com.aleprimo.nova_store.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "first_name", nullable = false, length = 50)
    String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    String lastName;
    @Column(name = "email", nullable = false, unique = true, length = 100)
    String email;
    @Column(name = "is_active", nullable = false)
    Boolean isActive;
    @Column(name = "created_at", nullable = false, updatable = false)
     LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
