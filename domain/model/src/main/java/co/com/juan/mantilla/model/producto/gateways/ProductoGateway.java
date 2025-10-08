package co.com.juan.mantilla.model.producto.gateways;

import co.com.juan.mantilla.model.producto.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoGateway {

    Mono<Producto> agregarProductoASucursal(Producto producto);
    
    Mono<Producto> modificarStock(Integer id, Integer newStock);

    Flux<Producto> obtenerProductoMayorStock();
}
