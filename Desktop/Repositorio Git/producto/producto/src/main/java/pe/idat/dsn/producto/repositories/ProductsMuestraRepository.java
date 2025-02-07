package pe.idat.dsn.producto.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.idat.dsn.producto.models.ProductsMuestra;

import java.math.BigDecimal;
import java.util.List;

public interface ProductsMuestraRepository extends JpaRepository<ProductsMuestra, Long> {

    // Buscar producto por código
    List<ProductsMuestra> findByCode(String code);

    // Buscar producto por nombre
    List<ProductsMuestra> findByName(String name);

    // Buscar producto por categoría
    List<ProductsMuestra> findByCategory(String category);

    // Buscar producto por precio
    List<ProductsMuestra> findByPrice(BigDecimal price);  // Cambio a BigDecimal

    // Buscar producto por nombre y categoría
    List<ProductsMuestra> findByNameAndCategory(String name, String category);

    // Buscar producto por nombre y precio
    List<ProductsMuestra> findByNameAndPrice(String name, BigDecimal price);  // Cambio a BigDecimal

    // Búsqueda flexible y paginada con múltiples filtros
    @Query("SELECT p FROM ProductsMuestra p WHERE "
            + "(:code IS NULL OR p.code LIKE %:code%) AND "
            + "(:name IS NULL OR p.name LIKE %:name%) AND "
            + "(:category IS NULL OR p.category LIKE %:category%) AND "
            + "(:price IS NULL OR p.price = :price) AND "
            + "(:stockQuantity IS NULL OR p.stockQuantity = :stockQuantity)")
    Page<ProductsMuestra> findByPropertiesPageable(
            @Param("code") String code,
            @Param("name") String name,
            @Param("category") String category,
            @Param("price") BigDecimal price,  // Cambio a BigDecimal
            @Param("stockQuantity") Integer stockQuantity,
            Pageable pageable);
}
