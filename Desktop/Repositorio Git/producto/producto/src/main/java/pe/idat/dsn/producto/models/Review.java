package pe.idat.dsn.producto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único de la reseña, con autoincremento

    @Column(name = "rating")
    private Integer rating;  // Calificación entre 1 y 5

    @Column(name = "comment", length = 1000)
    private String comment;  // Comentario de la reseña

    @Column(name = "reviewer_name", length = 255)
    private String reviewerName;  // Nombre del revisor

    @Column(name = "reviewer_email", length = 255)
    private String reviewerEmail;  // Correo electrónico del revisor

    @Column(name = "review_title", length = 255)
    private String reviewTitle;  // Título corto para la reseña

    @Column(name = "helpful_votes")
    private Integer helpfulVotes = 1;  // Votos útiles, por defecto 1

    @Column(name = "is_verified")
    private Boolean isVerified = false;  // Si la reseña está verificada o no (false = No, true = Sí)
}
