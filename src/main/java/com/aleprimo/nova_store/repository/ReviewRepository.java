package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
//    boolean existById(Long id);
}
