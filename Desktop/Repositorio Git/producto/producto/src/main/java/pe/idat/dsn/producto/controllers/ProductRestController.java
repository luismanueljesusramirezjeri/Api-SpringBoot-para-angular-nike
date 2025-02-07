package pe.idat.dsn.producto.controllers;

import java.util.List;

import pe.idat.dsn.producto.dtos.GetByPropertyPageableFilter;
import pe.idat.dsn.producto.models.Product;
import pe.idat.dsn.producto.services.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "*")
public class ProductRestController {

    private final ProductService productservice;

    public ProductRestController(ProductService productservice) {
        this.productservice = productservice;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productservice.getAll());
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product response = productservice.insert(product); 
        if (response == null || response.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateClient(@PathVariable long id, @RequestBody Product product) {
        var response = productservice.update(id, product);
        if (response == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Server-Message", "No se ha actualizado el registro con ID: " + id);
            return ResponseEntity.badRequest().headers(headers).build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        boolean hasDeleted = productservice.delete(id);
        if (!hasDeleted) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header("X-Server-Message", "No se ha encontrado el registro con ID: " + id)
                .build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Server-Message", "Producto con ID " + id + " eliminado correctamente");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Product>> getByPropertiesPageable(
        @RequestParam(name = "code", defaultValue = "") String code,
        @RequestParam(name = "name", defaultValue = "") String name,
        @RequestParam(name = "category", defaultValue = "") String category,
        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
        @RequestParam(name = "pageSize", defaultValue = "2") int pageSize) {

            GetByPropertyPageableFilter filter = new GetByPropertyPageableFilter(pageNumber, pageSize, code, name, category);


        Page<Product> products = productservice.getByPropertiesPageable(filter);
        
        return ResponseEntity.ok(products);
    }
}
