package co.com.juan.mantilla.r2dbc.producto;

import co.com.juan.mantilla.model.producto.Producto;
import co.com.juan.mantilla.model.producto.gateways.ProductoGateway;
import co.com.juan.mantilla.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ProductoRepositoryAdapter extends ReactiveAdapterOperations<Producto, ProductoEntity, Integer, ProductoRepository> implements ProductoGateway {
    
    public ProductoRepositoryAdapter(ProductoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Producto.class));
    }

    @Override
    public Mono<Producto> agregarProductoASucursal(Producto producto) {
        log.info("Ejecutando el guardado de entidad Producto");
        return repository.save(mapper.map(producto, ProductoEntity.class))
                .map(this::toEntity)
                .onErrorResume(
                        e -> {
                            log.error("Error ejecutando el guardado de la entidad Producto", e);
                            return Mono.error(new RuntimeException("Error al intentar guardar la entidad Producto", e));
                        });
    }


    @Override
    public Mono<Producto> modificarStock(Integer id, Integer newStock) {
        log.info("Ejecutando la modificación del Stock de Producto");
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setStock(newStock);
                    return repository.save(entity);
                })
                .map(this::toEntity)
                .onErrorResume(
                        e -> {
                            log.error("Error al modificar el Stock de la entidad Producto", e);
                            return Mono.error(new RuntimeException("Error al modificar el Stock de Producto", e));
                        }
                );
    }

    public Flux<Producto> obtenerProductoMayorStock() {
        log.info("Obteniendo los productos con mayor Stock");
        return repository.buscarProductosConMayorStock()
                .map(v -> Producto.builder()
                        .id(v.getId())
                        .nombre(v.getNombre())
                        .stock(v.getStock())
                        .sucursalId(v.getSucursalId())
                        .build()
                ).onErrorResume(
                        e -> {
                            log.error("Error al obtener los Productos con mayor Stock", e);
                            return Flux.error(new RuntimeException("Error al obtener Productos con mayor Stock", e));
                        }
                );
    }
}
