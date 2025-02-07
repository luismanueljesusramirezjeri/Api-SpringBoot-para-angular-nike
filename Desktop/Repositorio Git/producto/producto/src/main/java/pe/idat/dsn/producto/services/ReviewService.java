package pe.idat.dsn.producto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.idat.dsn.producto.models.Review;
import pe.idat.dsn.producto.repositories.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // Obtener todas las reseñas
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    // Obtener una reseña por su ID
    public Review getById(long id) {
        Optional<Review> result = reviewRepository.findById(id);
        return result.orElse(null);
    }

    // Insertar una nueva reseña
    public Review insert(Review entity) {
        return reviewRepository.saveAndFlush(entity);
    }

    // Eliminar una reseña por su ID
    public boolean delete(long id) {
        Optional<Review> result = reviewRepository.findById(id);
        if (!result.isPresent()) {
            return false;
        }
        reviewRepository.delete(result.get());
        return true;
    }

    // Actualizar la información de una reseña
    public Review update(long id, Review review) {
        Optional<Review> result = reviewRepository.findById(id);
        if (!result.isPresent()) {
            return null;
        }

        Review reviewRemoteState = result.get();
        reviewRemoteState.setRating(review.getRating());
        reviewRemoteState.setComment(review.getComment());
        reviewRemoteState.setReviewerName(review.getReviewerName());
        reviewRemoteState.setReviewerEmail(review.getReviewerEmail());
        reviewRemoteState.setReviewTitle(review.getReviewTitle());
        reviewRemoteState.setHelpfulVotes(review.getHelpfulVotes());
        reviewRemoteState.setIsVerified(review.getIsVerified());

        return reviewRepository.saveAndFlush(reviewRemoteState);
    }

    // Búsqueda flexible y paginada de reseñas con filtros
    public Page<Review> searchReviews(Integer rating, String reviewTitle, String reviewerEmail, String reviewerName,
                                      Boolean isVerified, Pageable pageable) {
        return reviewRepository.findByPropertiesPageable(rating, reviewTitle, reviewerEmail, reviewerName, isVerified, pageable);
    }
}
