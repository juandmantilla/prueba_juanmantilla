package co.com.juan.mantilla.r2dbc.franquicia;

import co.com.juan.mantilla.model.franquicia.Franquicia;
import co.com.juan.mantilla.model.franquicia.gateways.FranquiciaGateway;
import co.com.juan.mantilla.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Component
public class FranquiciaRepositoryAdapter extends ReactiveAdapterOperations<Franquicia, FranquiciaEntity, Integer, FranquiciaRepository> implements FranquiciaGateway {

    public FranquiciaRepositoryAdapter(FranquiciaRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franquicia.class));
    }

    @Override
    public Mono<Franquicia> agregarFranquicia(Franquicia franquicia) {
        return repository.save(mapper.map(franquicia, FranquiciaEntity.class)).map(this::toEntity);
    }

    @Override
    public Mono<Franquicia> actualizarNombreFranquicia(String nombreFranquicia, Integer id) {

        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setNombre(nombreFranquicia);
                    return repository.save(entity);
                }).map(this::toEntity);
    }
}
