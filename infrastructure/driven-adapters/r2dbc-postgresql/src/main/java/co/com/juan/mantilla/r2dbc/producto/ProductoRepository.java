package co.com.juan.mantilla.r2dbc.producto;

import co.com.juan.mantilla.r2dbc.producto.view.ProductoSucursalView;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductoRepository extends ReactiveCrudRepository<ProductoEntity, Integer>, ReactiveQueryByExampleExecutor<ProductoEntity> {

    @Query("""
            SELECT *
            FROM producto pr
            INNER JOIN sucursal su\s
                ON pr.sucursal_id = su.id
            ORDER BY pr.stock DESC
           \s""")
    Flux<ProductoSucursalView> buscarProductosConMayorStock();
}
