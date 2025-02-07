package pe.idat.dsn.producto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class ProductsMuestra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único del producto

    @Column(name = "code", length = 50, nullable = false)
    private String code;  // Código único del producto

    @Column(name = "name", length = 100, nullable = false)
    private String name;  // Nombre del producto

    @Column(name = "description", length = 100)
    private String description;  // Descripción del producto (opcional)

    @Column(name = "price", nullable = false)
    private BigDecimal price;  // Precio del producto (usando BigDecimal para precisión)

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;  // Cantidad en stock (con valor predeterminado 0)

    @Column(name = "category", length = 50)
    private String category;  // Categoría del producto (opcional)

    @Column(name = "image_url", length = 255)
    private String imageUrl;  // Ruta o URL de la imagen del producto (opcional)

    @Column(name = "image_loaded", nullable = false, columnDefinition = "BIT DEFAULT 0")
    private Boolean imageLoaded = false;  // Estado: false = No cargada, true = Cargada
}
