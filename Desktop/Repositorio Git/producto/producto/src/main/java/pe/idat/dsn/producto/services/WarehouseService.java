package pe.idat.dsn.producto.services;

import org.springframework.stereotype.Service;
import pe.idat.dsn.producto.models.Warehouse;
import pe.idat.dsn.producto.repositories.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    // Constructor con inyección de dependencias
    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    // Obtener todos los almacenes
    public List<Warehouse> getAll() {
        return warehouseRepository.findAll();
    }

    // Obtener un almacén por su ID
    public Warehouse getById(long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    // Insertar un nuevo almacén
    public Warehouse insert(Warehouse warehouse) {
        return warehouseRepository.save(warehouse); // guardado sin necesidad de flush explícito
    }

    // Eliminar un almacén por su ID
    public boolean delete(long id) {
        Optional<Warehouse> result = warehouseRepository.findById(id);
        if (result.isPresent()) {
            warehouseRepository.delete(result.get());
            return true;
        }
        return false;
    }

    // Actualizar la información de un almacén
    public Warehouse update(long id, Warehouse warehouse) {
        Optional<Warehouse> result = warehouseRepository.findById(id);
        if (!result.isPresent()) {
            return null; // No se encontró el almacén
        }

        Warehouse warehouseToUpdate = result.get();
        warehouseToUpdate.setName(warehouse.getName());
        warehouseToUpdate.setLocation(warehouse.getLocation());
        warehouseToUpdate.setCapacity(warehouse.getCapacity());
        warehouseToUpdate.setStatus(warehouse.getStatus());
        warehouseToUpdate.setManagerName(warehouse.getManagerName());
        warehouseToUpdate.setManagerContact(warehouse.getManagerContact());

        return warehouseRepository.save(warehouseToUpdate); // Se guarda el almacén actualizado
    }

    // Búsqueda paginada de almacenes con filtros
    public Page<Warehouse> getByPropertiesPageable(String name, String location, String capacity, String status, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Integer capacityParsed = (capacity != null && !capacity.isEmpty()) ? Integer.parseInt(capacity) : null;
        return warehouseRepository.findByPropertiesPageable(name, location, capacityParsed, status, pageable);
    }

    // Obtener almacenes por nombre
    public List<Warehouse> getByName(String name) {
        return warehouseRepository.findByName(name);
    }

    // Obtener almacenes por ubicación
    public List<Warehouse> getByLocation(String location) {
        return warehouseRepository.findByLocation(location);
    }

    // Obtener almacenes por capacidad
    public List<Warehouse> getByCapacity(Integer capacity) {
        return warehouseRepository.findByCapacity(capacity);
    }

    // Obtener almacenes por estado
    public List<Warehouse> getByStatus(String status) {
        return warehouseRepository.findByStatus(status);
    }
}
