package pe.idat.dsn.producto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.idat.dsn.producto.models.Product;




@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetProduct {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String category;
    @JsonIgnore
    private String messageCode;
    @JsonIgnore
    private String message;


public static GetProduct toDto(Product product){

    var dto = new GetProduct();

    if (product==null){

        dto.setMessageCode("204");
        dto.setMessage("El producto no existe ");
        
        return dto;
    }
    return new GetProduct(

    product.getId(),
    product.getCode(),
    product.getName(),
    product.getDescription(),
    product.getPrice(),
    product.getStockQuantity(),
    product.getCategory(),
    "200",
    "El producto ha sido recuperado correctamente"



    );



}



}