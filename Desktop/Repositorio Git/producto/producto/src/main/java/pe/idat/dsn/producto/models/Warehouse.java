package pe.idat.dsn.producto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único del almacén

    @Column(name = "name", length = 100)
    private String name;  // Nombre del almacén

    @Column(name = "location", length = 100)
    private String location;  // Ubicación del almacén

    @Column(name = "capacity")
    private Integer capacity;  // Capacidad total del almacén

    @Column(name = "status", columnDefinition = "ENUM('active', 'inactive') DEFAULT 'active'")
    private String status = "active";  // Estado del almacén (activo o inactivo)

    @Column(name = "manager_name", length = 100)
    private String managerName;  // Nombre del encargado del almacén (opcional)

    @Column(name = "manager_contact", length = 50)
    private String managerContact;  // Contacto del encargado (opcional)

}
