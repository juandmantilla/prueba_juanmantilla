package co.com.juan.mantilla.model.producto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Producto {
    private Integer id;
    private String nombre;
    private Integer stock;
    private Integer sucursalId;
}
