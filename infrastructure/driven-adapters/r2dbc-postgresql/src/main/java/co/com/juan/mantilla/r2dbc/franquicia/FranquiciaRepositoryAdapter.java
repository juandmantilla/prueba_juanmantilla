package co.com.juan.mantilla.r2dbc.franquicia;

import co.com.juan.mantilla.model.franquicia.Franquicia;
import co.com.juan.mantilla.model.franquicia.gateways.FranquiciaGateway;
import co.com.juan.mantilla.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.logging.Handler;

@Component
public class FranquiciaRepositoryAdapter extends ReactiveAdapterOperations<Franquicia, FranquiciaEntity, Integer, FranquiciaRepository> implements FranquiciaGateway {

    private static final Logger log = LoggerFactory.getLogger(Handler.class);

    public FranquiciaRepositoryAdapter(FranquiciaRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franquicia.class));
    }

    @Override
    public Mono<Franquicia> agregarFranquicia(Franquicia franquicia) {
        log.info("Ejecutando el guardado de la entidad Franquicia: {}", franquicia);
        return repository.save(mapper.map(franquicia, FranquiciaEntity.class))
                .map(this::toEntity)
                .onErrorResume(e -> {
                            log.error("Error al ejecutar el guardado de la Franquicia", e);
                            return Mono.error(new RuntimeException("No se pudo guardar la entidad Franquicia:", e));
                        }
                );
    }

    @Override
    public Mono<Franquicia> actualizarNombreFranquicia(String nombreFranquicia, Integer id) {

        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setNombre(nombreFranquicia);
                    return repository.save(entity);
                })
                .map(this::toEntity)
                .onErrorResume(e -> {
                            log.error("Error al actualizar de la Franquicia", e);
                            return Mono.error(new RuntimeException("No se pudo actualizar la entidad Franquicia:", e));
                        }
                );
    }
}
