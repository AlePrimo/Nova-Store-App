package com.aleprimo.nova_store.models;

import com.aleprimo.nova_store.models.enums.RoleName;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    @EqualsAndHashCode.Include
    RoleName name;


}
