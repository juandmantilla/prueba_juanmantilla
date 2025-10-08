package co.com.juan.mantilla.model.franquicia;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Franquicia {
    private Integer id;
    private String nombre;
}
