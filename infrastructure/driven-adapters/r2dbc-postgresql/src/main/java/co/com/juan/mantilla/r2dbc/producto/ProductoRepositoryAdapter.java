package co.com.juan.mantilla.r2dbc.producto;

import co.com.juan.mantilla.model.producto.Producto;
import co.com.juan.mantilla.model.producto.gateways.ProductoGateway;
import co.com.juan.mantilla.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProductoRepositoryAdapter extends ReactiveAdapterOperations<Producto, ProductoEntity, Integer, ProductoRepository> implements ProductoGateway {


    public ProductoRepositoryAdapter(ProductoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Producto.class));
    }

    @Override
    public Mono<Producto> agregarProductoASucursal(Producto producto) {
        return repository.save(mapper.map(producto, ProductoEntity.class)).map(this::toEntity);
    }


    @Override
    public Mono<Producto> modificarStock(Integer id, Integer newStock) {

        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setStock(newStock);
                    return repository.save(entity);
                }).map(this::toEntity);
    }
}
