package co.com.juan.mantilla.r2dbc.franquicia;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FranquiciaRepository extends ReactiveCrudRepository<FranquiciaEntity, Integer>, ReactiveQueryByExampleExecutor<FranquiciaEntity> {


}
