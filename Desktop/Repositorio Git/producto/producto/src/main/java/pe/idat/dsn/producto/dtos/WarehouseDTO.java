package pe.idat.dsn.producto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.idat.dsn.producto.models.Warehouse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDTO {

    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private String status;
    private String managerName;
    private String managerContact;

    @JsonIgnore
    private String messageCode;
    @JsonIgnore
    private String message;

    // Método estático para convertir un modelo de Warehouse a DTO
    public static WarehouseDTO toDto(Warehouse warehouse) {
        // Si warehouse es nulo, se devuelve el DTO con un mensaje adecuado
        if (warehouse == null) {
            return new WarehouseDTO(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "204",
                    "El almacén no existe"
            );
        }

        // Si no es nulo, se retorna el DTO con los datos del warehouse
        return new WarehouseDTO(
                warehouse.getId(),
                warehouse.getName(),
                warehouse.getLocation(),
                warehouse.getCapacity(),
                warehouse.getStatus(),
                warehouse.getManagerName(),
                warehouse.getManagerContact(),
                "200",
                "El almacén ha sido recuperado correctamente"
        );
    }

    // Método estático para convertir un DTO a un modelo Warehouse
    public static Warehouse fromDto(WarehouseDTO warehouseDTO) {
        // Si el DTO es nulo, se retorna null
        if (warehouseDTO == null) {
            return null;
        }

        // Retornamos un nuevo Warehouse con los valores del DTO
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseDTO.getId());
        warehouse.setName(warehouseDTO.getName());
        warehouse.setLocation(warehouseDTO.getLocation());
        warehouse.setCapacity(warehouseDTO.getCapacity());
        warehouse.setStatus(warehouseDTO.getStatus());
        warehouse.setManagerName(warehouseDTO.getManagerName());
        warehouse.setManagerContact(warehouseDTO.getManagerContact());

        return warehouse;
    }
}
