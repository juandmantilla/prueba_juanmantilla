package co.com.juan.mantilla.r2dbc.producto;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductoRepository extends ReactiveCrudRepository<ProductoEntity, Integer>, ReactiveQueryByExampleExecutor<ProductoEntity> {
    
}
