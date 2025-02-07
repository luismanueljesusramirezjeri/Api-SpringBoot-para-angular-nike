package pe.idat.dsn.producto.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.idat.dsn.producto.models.Review;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Buscar reseña por calificación
    List<Review> findByRating(Integer rating);

    // Buscar reseña por título
    List<Review> findByReviewTitle(String reviewTitle);

    // Buscar reseña por correo electrónico del revisor
    List<Review> findByReviewerEmail(String reviewerEmail);

    // Buscar reseña por estado verificado
    List<Review> findByIsVerified(Boolean isVerified);

    // Buscar reseña por nombre del revisor
    List<Review> findByReviewerName(String reviewerName);

    // Búsqueda flexible y paginada con múltiples filtros
    @Query("SELECT r FROM Review r WHERE "
            + "(:rating IS NULL OR r.rating = :rating) AND "
            + "(:reviewTitle IS NULL OR r.reviewTitle LIKE %:reviewTitle%) AND "
            + "(:reviewerEmail IS NULL OR r.reviewerEmail LIKE %:reviewerEmail%) AND "
            + "(:reviewerName IS NULL OR r.reviewerName LIKE %:reviewerName%) AND "
            + "(:isVerified IS NULL OR r.isVerified = :isVerified)")
    Page<Review> findByPropertiesPageable(
            @Param("rating") Integer rating,
            @Param("reviewTitle") String reviewTitle,
            @Param("reviewerEmail") String reviewerEmail,
            @Param("reviewerName") String reviewerName,
            @Param("isVerified") Boolean isVerified,
            Pageable pageable);
}
