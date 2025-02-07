package pe.idat.dsn.producto.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.idat.dsn.producto.models.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    // Buscar proveedor por nombre
    Optional<Supplier> findByName(String name);

    // Buscar proveedores por contacto
    List<Supplier> findByContactInfo(String contactInfo);

    // Buscar proveedores por nombre y contacto
    List<Supplier> findByNameAndContactInfo(String name, String contactInfo);

    // Búsqueda flexible y paginada con múltiples filtros
    @Query("SELECT s FROM Supplier s WHERE "
            + "(:name IS NULL OR s.name LIKE %:name%) AND "
            + "(:contactInfo IS NULL OR s.contactInfo LIKE %:contactInfo%) AND "
            + "(:email IS NULL OR s.email LIKE %:email%) AND "
            + "(:phoneNumber IS NULL OR s.phoneNumber LIKE %:phoneNumber%) AND "
            + "(:address IS NULL OR s.address LIKE %:address%) AND "
            + "(:city IS NULL OR s.city LIKE %:city%) AND "
            + "(:country IS NULL OR s.country LIKE %:country%) AND "
            + "(:websiteUrl IS NULL OR s.websiteUrl LIKE %:websiteUrl%)")
    Page<Supplier> findByPropertiesPageable(
            @Param("name") String name,
            @Param("contactInfo") String contactInfo,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber,
            @Param("address") String address,
            @Param("city") String city,
            @Param("country") String country,
            @Param("websiteUrl") String websiteUrl,
            Pageable pageable);
}
