package co.com.juan.mantilla.model.sucursal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Sucursal {

    private Integer id;
    private String nombre;
    private Integer franquiciaId;
}
