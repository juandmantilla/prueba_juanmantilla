package co.com.juan.mantilla.r2dbc.sucursal;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SucursalReposiorio extends ReactiveCrudRepository<SucursalEntidad, Integer>, ReactiveQueryByExampleExecutor<SucursalEntidad> {
}
