package pe.idat.dsn.producto.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.idat.dsn.producto.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCode(String code);

    @Query("SELECT p FROM Product p WHERE p.category LIKE %:category%")
    List<Product> findByCategory(@Param("category") String category);
    
    List<Product> findByName(String name);
   
    List<Product> findByNameAndCategory(String name, String category);
    
    @Query("SELECT p FROM Product p WHERE "
            + "(:code IS NULL OR p.code = :code) AND "
            + "(:name IS NULL OR p.name LIKE %:name%) AND "
            + "(:category IS NULL OR p.category LIKE %:category%) AND "
            + "(:description IS NULL OR p.description LIKE %:description%)")
    Page<Product> findByPropertiesPageable(
            @Param("code") String code,
            @Param("name") String name,
            @Param("category") String category,
            @Param("description") String description,
            Pageable pageable);
}
