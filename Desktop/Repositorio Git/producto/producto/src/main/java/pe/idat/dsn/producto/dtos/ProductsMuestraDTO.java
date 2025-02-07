package pe.idat.dsn.producto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.idat.dsn.producto.models.ProductsMuestra;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsMuestraDTO {

    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;  // Cambiado a BigDecimal para mayor precisión
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private Boolean imageLoaded;

    @JsonIgnore
    private String messageCode;
    @JsonIgnore
    private String message;

    // Método estático para convertir un modelo de ProductsMuestra a DTO
    public static ProductsMuestraDTO toDto(ProductsMuestra product) {
        // Si el producto es nulo, se devuelve el DTO con un mensaje adecuado
        if (product == null) {
            return new ProductsMuestraDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "204",
                "El producto no existe"
            );
        }

        // Si no es nulo, se retorna el DTO con los datos del producto
        return new ProductsMuestraDTO(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory(),
                product.getImageUrl(),
                product.getImageLoaded(),
                "200",
                "El producto ha sido recuperado correctamente"
        );
    }

    // Método estático para convertir un DTO a un modelo ProductsMuestra
    public static ProductsMuestra fromDto(ProductsMuestraDTO productDTO) {
        // Si el DTO es nulo, se retorna null
        if (productDTO == null) {
            return null;
        }

        // Retornamos un nuevo ProductsMuestra con los valores del DTO
        ProductsMuestra product = new ProductsMuestra();
        product.setId(productDTO.getId());
        product.setCode(productDTO.getCode());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setCategory(productDTO.getCategory());
        product.setImageUrl(productDTO.getImageUrl());
        product.setImageLoaded(productDTO.getImageLoaded());

        return product;
    }
}
