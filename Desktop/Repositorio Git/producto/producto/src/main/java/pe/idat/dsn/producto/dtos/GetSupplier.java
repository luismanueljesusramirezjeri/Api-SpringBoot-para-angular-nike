package pe.idat.dsn.producto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.idat.dsn.producto.models.Supplier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSupplier {

    private Long id;
    private String name;
    private String contactInfo;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private String websiteUrl;

    @JsonIgnore
    private String messageCode;
    @JsonIgnore
    private String message;

    // Método de conversión de Supplier a GetSupplier (DTO)
    public static GetSupplier toDto(Supplier supplier) {

        var dto = new GetSupplier();

        if (supplier == null) {
            dto.setMessageCode("204");
            dto.setMessage("El proveedor no existe");
            return dto;
        }

        return new GetSupplier(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactInfo(),
                supplier.getEmail(),
                supplier.getPhoneNumber(),
                supplier.getAddress(),
                supplier.getCity(),
                supplier.getCountry(),
                supplier.getWebsiteUrl(),
                "200",
                "El proveedor ha sido recuperado correctamente"
        );
    }
}
