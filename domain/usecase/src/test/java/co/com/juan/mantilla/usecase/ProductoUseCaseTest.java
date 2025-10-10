package co.com.juan.mantilla.usecase;

import co.com.juan.mantilla.model.producto.Producto;
import co.com.juan.mantilla.model.producto.puertos.ProductoPuerto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ProductoUseCaseTest {

    private ProductoPuerto puerto;
    private ProductoUseCase productoUseCase;

    @BeforeEach
    void setUp() {
        puerto = Mockito.mock(ProductoPuerto.class);
        productoUseCase = new ProductoUseCase(puerto);
    }

    @Test
    void debeAgregarProductoASucursal() {

        Producto producto = new Producto(1, "Gaseosa", 20, 1);
        when(puerto.agregarProductoASucursal(any(Producto.class))).thenReturn(Mono.just(producto));

        StepVerifier.create(productoUseCase.agregarProductoASucursal(producto))
                .expectNext(producto)
                .verifyComplete();

        verify(puerto, times(1)).agregarProductoASucursal(any(Producto.class));
    }

    @Test
    void debeModificarStock() {

        Producto producto = new Producto(1, "Galletas", 50, 2);
        when(puerto.modificarStock(eq(1), eq(50))).thenReturn(Mono.just(producto));

        StepVerifier.create(productoUseCase.modificarStock(producto))
                .expectNext(producto)
                .verifyComplete();

        verify(puerto, times(1)).modificarStock(eq(1), eq(50));
    }

    @Test
    void debeObtenerProductosConMayorStock() {

        Producto p1 = new Producto(1, "Leche", 100, 3);
        Producto p2 = new Producto(2, "Café", 90, 3);
        when(puerto.obtenerProductoMayorStock()).thenReturn(Flux.just(p1, p2));

        StepVerifier.create(productoUseCase.obtenerProductoMayorStock())
                .expectNext(p1)
                .expectNext(p2)
                .verifyComplete();

        verify(puerto, times(1)).obtenerProductoMayorStock();
    }

    @Test
    void debePropagarErrorSiFallaElRepositorio() {
        
        Producto producto = new Producto(1, "Arroz", 10, 1);
        when(puerto.agregarProductoASucursal(any())).thenReturn(Mono.error(new RuntimeException("Error en BD")));


        StepVerifier.create(productoUseCase.agregarProductoASucursal(producto))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Error en BD"))
                .verify();

        verify(puerto, times(1)).agregarProductoASucursal(any());
    }
}