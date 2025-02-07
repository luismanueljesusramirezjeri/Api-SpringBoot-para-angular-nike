package pe.idat.dsn.producto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.idat.dsn.producto.models.Review;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReview {

    private Long id;
    private Integer rating;
    private String comment;
    private String reviewerName;
    private String reviewerEmail;
    private String reviewTitle;
    private Integer helpfulVotes;
    private Boolean isVerified;

    @JsonIgnore
    private String messageCode;
    @JsonIgnore
    private String message;

    // Método de conversión de Review a GetReview (DTO)
    public static GetReview toDto(Review review) {

        var dto = new GetReview();

        if (review == null) {
            dto.setMessageCode("204");
            dto.setMessage("La reseña no existe");
            return dto;
        }

        return new GetReview(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getReviewerName(),
                review.getReviewerEmail(),
                review.getReviewTitle(),
                review.getHelpfulVotes(),
                review.getIsVerified(),
                "200",
                "La reseña ha sido recuperada correctamente"
        );
    }
}
