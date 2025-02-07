package pe.idat.dsn.producto.controllers;

import pe.idat.dsn.producto.dtos.GetSupplier;
import pe.idat.dsn.producto.models.Supplier;
import pe.idat.dsn.producto.services.SupplierService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier")
@CrossOrigin(origins = "*")
public class SupplierRestController {

    private final SupplierService supplierService;

    public SupplierRestController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // Obtener todos los proveedores
    @GetMapping()
    public ResponseEntity<List<GetSupplier>> getAll() {
        List<Supplier> suppliers = supplierService.getAll();
        List<GetSupplier> suppliersDto = suppliers.stream()
                .map(GetSupplier::toDto)
                .toList();
        return ResponseEntity.ok(suppliersDto);
    }

    // Crear un nuevo proveedor
    @PostMapping()
    public ResponseEntity<GetSupplier> createSupplier(@RequestBody Supplier supplier) {
        Supplier response = supplierService.insert(supplier);
        if (response == null || response.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(GetSupplier.toDto(response));
    }

    // Actualizar un proveedor existente
    @PutMapping("/{id}")
    public ResponseEntity<GetSupplier> updateSupplier(@PathVariable long id, @RequestBody Supplier supplier) {
        Supplier response = supplierService.update(id, supplier);
        if (response == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Server-Message", "No se ha actualizado el registro con ID: " + id);
            return ResponseEntity.badRequest().headers(headers).build();
        }
        return ResponseEntity.ok(GetSupplier.toDto(response));
    }

    // Eliminar un proveedor por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable long id) {
        boolean hasDeleted = supplierService.delete(id);
        if (!hasDeleted) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header("X-Server-Message", "No se ha encontrado el registro con ID: " + id)
                .build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Message", "Proveedor con ID " + id + " eliminado correctamente");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

}
