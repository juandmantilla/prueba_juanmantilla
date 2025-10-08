package co.com.juan.mantilla.r2dbc.sucursal;

import co.com.juan.mantilla.model.sucursal.Sucursal;
import co.com.juan.mantilla.model.sucursal.gateways.SucursalGateway;
import co.com.juan.mantilla.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SucursalRepositoryAdapter extends ReactiveAdapterOperations<Sucursal, SucursalEntity, Integer, SucursalRepository> implements SucursalGateway {

    public SucursalRepositoryAdapter(SucursalRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Sucursal.class));
    }

    @Override
    public Mono<Sucursal> agregarSucursalAFranquicia(Sucursal sucursal) {
        return repository.save(mapper.map(sucursal, SucursalEntity.class)).map(this::toEntity);
    }

    @Override
    public Mono<Sucursal> actualizarNombreSucursal(String nombreSucursal, Integer id) {
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setNombre(nombreSucursal);
                    return repository.save(entity);
                }).map(this::toEntity);
    }
}
