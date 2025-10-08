package co.com.juan.mantilla.r2dbc.sucursal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("sucursal")
public class SucursalEntity {

    @Id
    private Integer id;
    private String nombre;

    @Column("franquicia_id")
    private Integer franquiciaId;
}
