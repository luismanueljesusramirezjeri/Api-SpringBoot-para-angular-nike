package pe.idat.dsn.producto.controllers;

import java.util.List;
import java.util.stream.Collectors;

import pe.idat.dsn.producto.dtos.ProductsMuestraDTO;
import pe.idat.dsn.producto.models.ProductsMuestra;
import pe.idat.dsn.producto.services.ProductsMuestraService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
public class ProductsMuestraRestController {

    private final ProductsMuestraService productsMuestraService;

    public ProductsMuestraRestController(ProductsMuestraService productsMuestraService) {
        this.productsMuestraService = productsMuestraService;
    }

    // Obtener todos los productos
    @GetMapping()
    public ResponseEntity<List<ProductsMuestraDTO>> getAll() {
        List<ProductsMuestra> products = productsMuestraService.getAll();
        List<ProductsMuestraDTO> productsDTOs = products.stream()
                                                    .map(ProductsMuestraDTO::toDto)
                                                    .collect(Collectors.toList());
        return ResponseEntity.ok(productsDTOs);
    }

    // Crear un nuevo producto
    @PostMapping()
    public ResponseEntity<ProductsMuestraDTO> createProduct(@RequestBody ProductsMuestraDTO productDTO) {
        // Convertir DTO a modelo
        ProductsMuestra product = ProductsMuestraDTO.fromDto(productDTO);
        ProductsMuestra response = productsMuestraService.insert(product);

        // Comprobar si el producto se creó correctamente
        if (response == null || response.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }

        // Convertir el modelo nuevamente a DTO para la respuesta
        ProductsMuestraDTO responseDTO = ProductsMuestraDTO.toDto(response);
        return ResponseEntity.ok(responseDTO);
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<ProductsMuestraDTO> updateProduct(@PathVariable long id, @RequestBody ProductsMuestraDTO productDTO) {
        // Convertir DTO a modelo
        ProductsMuestra product = ProductsMuestraDTO.fromDto(productDTO);
        ProductsMuestra response = productsMuestraService.update(id, product);

        // Verificar si la actualización fue exitosa
        if (response == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Server-Message", "No se ha actualizado el producto con ID: " + id);
            return ResponseEntity.badRequest().headers(headers).build();
        }

        // Convertir el modelo actualizado a DTO para la respuesta
        ProductsMuestraDTO responseDTO = ProductsMuestraDTO.toDto(response);
        return ResponseEntity.ok(responseDTO);
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        boolean hasDeleted = productsMuestraService.delete(id);

        // Verificar si el producto fue eliminado
        if (!hasDeleted) {
            ProductsMuestraDTO productsDTO = new ProductsMuestraDTO();
            productsDTO.setMessageCode("404");
            productsDTO.setMessage("No se ha encontrado el producto con ID: " + id);

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Server-Message", productsDTO.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(productsDTO.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Message", "Producto con ID " + id + " eliminado correctamente");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
