package pe.idat.dsn.producto.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.idat.dsn.producto.models.Warehouse;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    // Buscar almacén por nombre
    List<Warehouse> findByName(String name);

    // Buscar almacén por ubicación
    List<Warehouse> findByLocation(String location);

    // Buscar almacén por capacidad
    List<Warehouse> findByCapacity(Integer capacity);

    // Buscar almacén por nombre y ubicación
    List<Warehouse> findByNameAndLocation(String name, String location);

    // Buscar almacén por nombre y estado
    List<Warehouse> findByNameAndStatus(String name, String status);

    // Buscar almacén por estado
    List<Warehouse> findByStatus(String status);

    // Búsqueda flexible y paginada con múltiples filtros
    @Query("SELECT w FROM Warehouse w WHERE "
            + "(:name IS NULL OR w.name LIKE %:name%) AND "
            + "(:location IS NULL OR w.location LIKE %:location%) AND "
            + "(:capacity IS NULL OR w.capacity = :capacity) AND "
            + "(:status IS NULL OR w.status = :status)")
    Page<Warehouse> findByPropertiesPageable(
            @Param("name") String name,
            @Param("location") String location,
            @Param("capacity") Integer capacity,
            @Param("status") String status,
            Pageable pageable);
}
