package co.com.juan.mantilla.usecase;

import co.com.juan.mantilla.model.producto.Producto;
import co.com.juan.mantilla.model.producto.gateways.ProductoGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class ProductoUseCase {

    //    private static final Logger log = LoggerFactory.getLogger(FranquiciaUseCase.class);
    private final ProductoGateway repository;

    public Mono<Producto> agregarProductoASucursal(Producto producto) {
        //log.info("Ejecutando el caso de uso de Agregar Producto a Sucursal");
        return repository.agregarProductoASucursal(producto);
    }

    public Mono<Producto> modificarStock(Producto producto) {
        //log.info("Ejecutando el caso de uso de Modificar Stock");
        return repository.modificarStock(producto.getId(), producto.getStock());
    }

    public Flux<Producto> obtenerProductoMayorStock() {
        //log.info("Ejecutando el caso de uso de Obtener Producto con Mayor Stock");
        return repository.obtenerProductoMayorStock();
    }

}
