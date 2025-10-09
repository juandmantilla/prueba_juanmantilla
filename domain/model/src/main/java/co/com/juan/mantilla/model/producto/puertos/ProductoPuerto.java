package co.com.juan.mantilla.model.producto.puertos;

import co.com.juan.mantilla.model.producto.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoPuerto {

    Mono<Producto> agregarProductoASucursal(Producto producto);
    
    Mono<Producto> modificarStock(Integer id, Integer newStock);

    Flux<Producto> obtenerProductoMayorStock();
}
