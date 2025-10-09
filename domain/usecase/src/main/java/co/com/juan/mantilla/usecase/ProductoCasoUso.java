package co.com.juan.mantilla.usecase;

import co.com.juan.mantilla.model.producto.Producto;
import co.com.juan.mantilla.model.producto.puertos.ProductoPuerto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class ProductoCasoUso {

    private final ProductoPuerto repository;

    public Mono<Producto> agregarProductoASucursal(Producto producto) {
        return repository.agregarProductoASucursal(producto);
    }

    public Mono<Producto> modificarStock(Producto producto) {
        return repository.modificarStock(producto.getId(), producto.getStock());
    }

    public Flux<Producto> obtenerProductoMayorStock() {
        return repository.obtenerProductoMayorStock();
    }

}
