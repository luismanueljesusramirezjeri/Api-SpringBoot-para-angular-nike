package pe.idat.dsn.producto.controllers;

import java.util.List;
import java.util.stream.Collectors;

import pe.idat.dsn.producto.dtos.WarehouseDTO;
import pe.idat.dsn.producto.models.Warehouse;
import pe.idat.dsn.producto.services.WarehouseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/warehouse")
@CrossOrigin(origins = "*")
public class WarehouseRestController {

    private final WarehouseService warehouseService;

    public WarehouseRestController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    // Obtener todos los almacenes
    @GetMapping()
    public ResponseEntity<List<WarehouseDTO>> getAll() {
        List<Warehouse> warehouses = warehouseService.getAll();
        List<WarehouseDTO> warehouseDTOs = warehouses.stream()
                                                    .map(WarehouseDTO::toDto)
                                                    .collect(Collectors.toList());
        return ResponseEntity.ok(warehouseDTOs);
    }

    // Crear un nuevo almacén
    @PostMapping()
    public ResponseEntity<WarehouseDTO> createWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse response = warehouseService.insert(warehouse);
        if (response == null || response.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        WarehouseDTO warehouseDTO = WarehouseDTO.toDto(response);
        return ResponseEntity.ok(warehouseDTO);
    }

    // Actualizar un almacén existente
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDTO> updateWarehouse(@PathVariable long id, @RequestBody Warehouse warehouse) {
        Warehouse response = warehouseService.update(id, warehouse);
        if (response == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Server-Message", "No se ha actualizado el registro con ID: " + id);
            return ResponseEntity.badRequest().headers(headers).build();
        }
        WarehouseDTO warehouseDTO = WarehouseDTO.toDto(response);
        return ResponseEntity.ok(warehouseDTO);
    }

    // Eliminar un almacén por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable long id) {
        boolean hasDeleted = warehouseService.delete(id);
        if (!hasDeleted) {
            // Ajustar el mensaje según el DTO
            WarehouseDTO warehouseDTO = new WarehouseDTO();
            warehouseDTO.setMessageCode("404");
            warehouseDTO.setMessage("No se ha encontrado el almacén con ID: " + id);

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Server-Message", warehouseDTO.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(warehouseDTO.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Message", "Almacén con ID " + id + " eliminado correctamente");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    // Búsqueda paginada de almacenes
    @GetMapping("/search")
    public ResponseEntity<List<WarehouseDTO>> searchWarehouses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String capacity,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        List<WarehouseDTO> warehouseDTOs = warehouseService.getByPropertiesPageable(name, location, capacity, status, pageNumber, pageSize)
                .stream()
                .map(WarehouseDTO::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(warehouseDTOs);
    }
}
