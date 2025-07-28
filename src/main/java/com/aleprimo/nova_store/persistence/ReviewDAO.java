package com.aleprimo.nova_store.persistence;


import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewDAO {
    ReviewResponseDTO save(ReviewResponseDTO dto);
    Page<ReviewResponseDTO> findAll(Pageable pageable);
    Optional<ReviewResponseDTO> findById(Long id);
    ReviewResponseDTO update(Long id, ReviewResponseDTO dto);
    void deleteById(Long id);
}
