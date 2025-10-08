package co.com.juan.mantilla.r2dbc.sucursal;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SucursalRepository extends ReactiveCrudRepository<SucursalEntity, Integer>, ReactiveQueryByExampleExecutor<SucursalEntity> {
}
