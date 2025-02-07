package pe.idat.dsn.producto.controllers;

import pe.idat.dsn.producto.dtos.GetReview;
import pe.idat.dsn.producto.models.Review;
import pe.idat.dsn.producto.services.ReviewService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@CrossOrigin(origins = "*")
public class ReviewRestController {

    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Obtener todas las reseñas
    @GetMapping()
    public ResponseEntity<List<GetReview>> getAll() {
        List<Review> reviews = reviewService.getAll();
        List<GetReview> reviewsDto = reviews.stream()
                .map(GetReview::toDto)
                .toList();
        return ResponseEntity.ok(reviewsDto);
    }

    // Crear una nueva reseña
    @PostMapping()
    public ResponseEntity<GetReview> createReview(@RequestBody Review review) {
        Review response = reviewService.insert(review);
        if (response == null || response.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(GetReview.toDto(response));
    }

    // Actualizar una reseña existente
    @PutMapping("/{id}")
    public ResponseEntity<GetReview> updateReview(@PathVariable long id, @RequestBody Review review) {
        Review response = reviewService.update(id, review);
        if (response == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Server-Message", "No se ha actualizado el registro con ID: " + id);
            return ResponseEntity.badRequest().headers(headers).build();
        }
        return ResponseEntity.ok(GetReview.toDto(response));
    }

    // Eliminar una reseña por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable long id) {
        boolean hasDeleted = reviewService.delete(id);
        if (!hasDeleted) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header("X-Server-Message", "No se ha encontrado el registro con ID: " + id)
                .build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Message", "Reseña con ID " + id + " eliminada correctamente");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
