package com.aleprimo.nova_store.entityServices.implementations;



import com.aleprimo.nova_store.controller.mappers.ReviewMapper;
import com.aleprimo.nova_store.dto.review.ReviewRequestDTO;
import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
import com.aleprimo.nova_store.entityServices.ReviewService;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.Review;
import com.aleprimo.nova_store.persistence.CustomerDAO;
import com.aleprimo.nova_store.persistence.ProductDAO;
import com.aleprimo.nova_store.persistence.ReviewDAO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDAO;
    private final ReviewMapper reviewMapper;
    private final CustomerDAO customerDAO;
    private final ProductDAO productDAO;

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO dto) {
        Review review = reviewMapper.toEntity(dto);
        Review saved = reviewDAO.save(review);
        return reviewMapper.toDTO(saved);
    }

    @Override
    public ReviewResponseDTO getReviewById(Long id) {
        Review review = reviewDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review no encontrada con id " + id));
        return reviewMapper.toDTO(review);
    }

    @Override
    public Page<ReviewResponseDTO> getAllReviews(Pageable pageable) {
        return reviewDAO.findAll(pageable).map(reviewMapper::toDTO);
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO dto) {
        Product product = productDAO.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id " + dto.getProductId()));
        Customer customer = customerDAO.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id " + dto.getCustomerId()));

        Review review = reviewDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review no encontrada con id " + id));
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setProduct(product);
        review.setCustomer(customer);
        Review updated = reviewDAO.save(review);
        return reviewMapper.toDTO(updated);
    }

    @Override
    public void deleteReview(Long id) {
        if (reviewDAO.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Review no encontrada con id " + id);
        }
        reviewDAO.deleteById(id);
    }
}