package pe.idat.dsn.producto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.idat.dsn.producto.models.Supplier;
import pe.idat.dsn.producto.repositories.SupplierRepository;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    // Obtener todos los proveedores
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    // Obtener un proveedor por su ID
    public Supplier getById(long id) {
        Optional<Supplier> result = supplierRepository.findById(id);
        return result.orElse(null);
    }

    // Insertar un nuevo proveedor
    public Supplier insert(Supplier entity) {
        return supplierRepository.saveAndFlush(entity);
    }

    // Eliminar un proveedor por su ID
    public boolean delete(long id) {
        Optional<Supplier> result = supplierRepository.findById(id);
        if (!result.isPresent()) {
            return false;
        }
        supplierRepository.delete(result.get());
        return true;
    }

    // Actualizar la información de un proveedor
    public Supplier update(long id, Supplier supplier) {
        Optional<Supplier> result = supplierRepository.findById(id);
        if (!result.isPresent()) {
            return null;
        }

        Supplier supplierRemoteState = result.get();
        supplierRemoteState.setName(supplier.getName());
        supplierRemoteState.setContactInfo(supplier.getContactInfo());
        supplierRemoteState.setEmail(supplier.getEmail());
        supplierRemoteState.setPhoneNumber(supplier.getPhoneNumber());
        supplierRemoteState.setAddress(supplier.getAddress());
        supplierRemoteState.setCity(supplier.getCity());
        supplierRemoteState.setCountry(supplier.getCountry());
        supplierRemoteState.setWebsiteUrl(supplier.getWebsiteUrl());

        return supplierRepository.saveAndFlush(supplierRemoteState);
    }

    // Búsqueda flexible y paginada de proveedores con filtros
    public Page<Supplier> searchSuppliers(String name, String contactInfo, String email, String phoneNumber, String address,
                                          String city, String country, String websiteUrl, Pageable pageable) {
        return supplierRepository.findByPropertiesPageable(name, contactInfo, email, phoneNumber, address, city, country, websiteUrl, pageable);
    }
}
