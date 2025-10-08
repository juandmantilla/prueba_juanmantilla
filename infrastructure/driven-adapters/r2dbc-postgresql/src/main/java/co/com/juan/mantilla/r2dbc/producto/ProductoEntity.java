package co.com.juan.mantilla.r2dbc.producto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("producto")
public class ProductoEntity {
    @Id
    private Integer id;
    private String nombre;
    private Integer stock;

    @Column("sucursal_id")
    private Integer sucursalId;
}
