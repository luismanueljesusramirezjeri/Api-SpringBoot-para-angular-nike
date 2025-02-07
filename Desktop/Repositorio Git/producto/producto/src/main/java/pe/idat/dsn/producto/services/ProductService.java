package pe.idat.dsn.producto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import pe.idat.dsn.producto.dtos.GetByPropertyPageableFilter;
import pe.idat.dsn.producto.models.Product;
import pe.idat.dsn.producto.repositories.ProductRepository;

@Service
public class ProductService {
    
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(long id) {
        Optional<Product> result = productRepository.findById(id);
        return result.orElse(null);
    }


    


    public Product insert(Product entity) {
        return productRepository.saveAndFlush(entity);
    }

    public boolean delete(long id) {
        Optional<Product> result = productRepository.findById(id);
        if (!result.isPresent()) {
            return false;
        }
        productRepository.delete(result.get());
        return true;
    }



    public Product update(long id, Product product){
        Optional<Product> result = productRepository.findById(id);
        if(!result.isPresent()){
            return null;
        }

        Product productRemoteState = result.get();
        productRemoteState.setCode(product.getCode());
        productRemoteState.setName(product.getName());
        productRemoteState.setDescription(product.getDescription());
        productRemoteState.setPrice(product.getPrice());
        productRemoteState.setStockQuantity(product.getStockQuantity());
        productRemoteState.setCategory(product.getCategory());

        return productRepository.saveAndFlush(productRemoteState);
    }


    public Page<Product> getByPropertiesPageable(GetByPropertyPageableFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
        return productRepository.findByPropertiesPageable(null, null, null, null, pageable);
    }
    
     

}
