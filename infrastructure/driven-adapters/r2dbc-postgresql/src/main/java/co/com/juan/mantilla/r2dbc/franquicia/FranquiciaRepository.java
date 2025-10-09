package co.com.juan.mantilla.r2dbc.franquicia;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FranquiciaRepository extends ReactiveCrudRepository<FranquiciaEntidad, Integer>, ReactiveQueryByExampleExecutor<FranquiciaEntidad> {


}
