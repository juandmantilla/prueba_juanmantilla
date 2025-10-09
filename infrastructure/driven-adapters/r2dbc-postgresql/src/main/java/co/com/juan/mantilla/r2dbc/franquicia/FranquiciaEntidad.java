package co.com.juan.mantilla.r2dbc.franquicia;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("franquicia")
public class FranquiciaEntidad {

    @Id
    private Integer id;
    private String nombre;
}
