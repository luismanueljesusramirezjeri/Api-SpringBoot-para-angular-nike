package pe.idat.dsn.producto.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.idat.dsn.producto.models.ProductsMuestra;
import pe.idat.dsn.producto.repositories.ProductsMuestraRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsMuestraService {

    private final ProductsMuestraRepository productsMuestraRepository;

    // Constructor con inyección de dependencias
    public ProductsMuestraService(ProductsMuestraRepository productsMuestraRepository) {
        this.productsMuestraRepository = productsMuestraRepository;
    }

    // Obtener todos los productos
    public List<ProductsMuestra> getAll() {
        return productsMuestraRepository.findAll();
    }

    // Obtener un producto por su ID
    public ProductsMuestra getById(long id) {
        return productsMuestraRepository.findById(id).orElse(null);
    }

    // Insertar un nuevo producto
    public ProductsMuestra insert(ProductsMuestra product) {
        return productsMuestraRepository.save(product); // guardado sin necesidad de flush explícito
    }

    // Eliminar un producto por su ID
    public boolean delete(long id) {
        Optional<ProductsMuestra> result = productsMuestraRepository.findById(id);
        if (result.isPresent()) {
            productsMuestraRepository.delete(result.get());
            return true;
        }
        return false;
    }

    // Actualizar la información de un producto
    public ProductsMuestra update(long id, ProductsMuestra product) {
        Optional<ProductsMuestra> result = productsMuestraRepository.findById(id);
        if (!result.isPresent()) {
            return null; // No se encontró el producto
        }

        ProductsMuestra productToUpdate = result.get();
        productToUpdate.setCode(product.getCode());
        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setStockQuantity(product.getStockQuantity());
        productToUpdate.setCategory(product.getCategory());
        productToUpdate.setImageUrl(product.getImageUrl());
        productToUpdate.setImageLoaded(product.getImageLoaded());

        return productsMuestraRepository.save(productToUpdate); // Se guarda el producto actualizado
    }

    // Búsqueda paginada de productos con filtros
    public Page<ProductsMuestra> getByPropertiesPageable(String code, String name, String category, BigDecimal price, Integer stockQuantity, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productsMuestraRepository.findByPropertiesPageable(code, name, category, price, stockQuantity, pageable);
    }

    // Obtener productos por código
    public List<ProductsMuestra> getByCode(String code) {
        return productsMuestraRepository.findByCode(code);
    }

    // Obtener productos por nombre
    public List<ProductsMuestra> getByName(String name) {
        return productsMuestraRepository.findByName(name);
    }

    // Obtener productos por categoría
    public List<ProductsMuestra> getByCategory(String category) {
        return productsMuestraRepository.findByCategory(category);
    }

    // Obtener productos por precio
    public List<ProductsMuestra> getByPrice(BigDecimal price) {
        return productsMuestraRepository.findByPrice(price);
    }

    // Obtener productos por nombre y categoría
    public List<ProductsMuestra> getByNameAndCategory(String name, String category) {
        return productsMuestraRepository.findByNameAndCategory(name, category);
    }

    // Obtener productos por nombre y precio
    public List<ProductsMuestra> getByNameAndPrice(String name, BigDecimal price) {
        return productsMuestraRepository.findByNameAndPrice(name, price);
    }
}
