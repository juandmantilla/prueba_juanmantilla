package co.com.juan.mantilla.r2dbc.sucursal;

import co.com.juan.mantilla.model.sucursal.Sucursal;
import co.com.juan.mantilla.model.sucursal.gateways.SucursalGateway;
import co.com.juan.mantilla.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SucursalRepositoryAdapter extends ReactiveAdapterOperations<Sucursal, SucursalEntity, Integer, SucursalRepository> implements SucursalGateway {

    public SucursalRepositoryAdapter(SucursalRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Sucursal.class));
    }

    @Override
    public Mono<Sucursal> agregarSucursalAFranquicia(Sucursal sucursal) {
        log.info("Ejecutando el guardado de entidad Sucursal");
        return repository
                .save(mapper.map(sucursal, SucursalEntity.class))
                .map(this::toEntity)
                .onErrorResume(
                        e -> {
                            log.error("Error ejecutando el guardado de la entidad Sucursal", e);
                            return Mono.error(new RuntimeException("Error al intentar guardar la entidad Sucursal", e));
                        });

    }

    @Override
    public Mono<Sucursal> actualizarNombreSucursal(String nombreSucursal, Integer id) {
        log.info("Ejecutando la actualización del nombre de Sucursal");
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setNombre(nombreSucursal);
                    return repository.save(entity);
                })
                .map(this::toEntity)
                .onErrorResume(
                        e -> {
                            log.error("Error ejecutando la actualización del nombre Sucursal", e);
                            return Mono.error(new RuntimeException("Error al actualizar la entidad Sucursal", e));
                        });
    }
}
