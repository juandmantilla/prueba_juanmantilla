package co.com.juan.mantilla.r2dbc.producto;

import co.com.juan.mantilla.model.producto.Producto;
import co.com.juan.mantilla.r2dbc.producto.view.ProductoSucursalView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ProductoRepositoryAdapterTest {

    @Mock
    private ProductoRepository repository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private ProductoRepositoryAdapter adapter;

    private Producto producto;
    private ProductoEntity entity;
    private ProductoSucursalView view;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        producto = Producto.builder()
                .id(1)
                .nombre("Producto Test")
                .stock(10)
                .sucursalId(5)
                .build();

        entity = new ProductoEntity();
        entity.setId(1);
        entity.setNombre("Producto Test");
        entity.setStock(10);
        entity.setSucursalId(5);

        view = new ProductoSucursalView() {
            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public String getNombre() {
                return "Producto Test";
            }

            @Override
            public Integer getStock() {
                return 10;
            }

            @Override
            public Integer getSucursalId() {
                return 5;
            }
        };
    }

    @Test
    void agregarProductoASucursal_DeberiaGuardarCorrectamente() {
        when(mapper.map(producto, ProductoEntity.class)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.map(entity, Producto.class)).thenReturn(producto);

        Mono<Producto> result = adapter.agregarProductoASucursal(producto);

        StepVerifier.create(result)
                .expectNext(producto)
                .verifyComplete();

        verify(repository, times(1)).save(entity);
    }

    @Test
    void agregarProductoASucursal_DeberiaLanzarErrorAlFallar() {
        when(mapper.map(producto, ProductoEntity.class)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.error(new RuntimeException("DB error")));

        Mono<Producto> result = adapter.agregarProductoASucursal(producto);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().contains("Error al intentar guardar la entidad Producto"))
                .verify();

        verify(repository).save(entity);
    }

    @Test
    void modificarStock_DeberiaActualizarStockCorrectamente() {
        when(repository.findById(1)).thenReturn(Mono.just(entity));
        when(repository.save(any(ProductoEntity.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        when(mapper.map(any(ProductoEntity.class), eq(Producto.class)))
                .thenAnswer(invocation -> {
                    ProductoEntity e = invocation.getArgument(0);
                    return Producto.builder()
                            .id(e.getId())
                            .nombre(e.getNombre())
                            .stock(e.getStock())
                            .sucursalId(e.getSucursalId())
                            .build();
                });

        Mono<Producto> result = adapter.modificarStock(1, 50);

        StepVerifier.create(result)
                .assertNext(p -> {
                    assertEquals(50, p.getStock());
                    assertEquals("Producto Test", p.getNombre());
                })
                .verifyComplete();

        verify(repository).findById(1);
        verify(repository).save(any(ProductoEntity.class));
    }

    @Test
    void modificarStock_DeberiaRetornarErrorSiFallaRepositorio() {
        when(repository.findById(1)).thenReturn(Mono.error(new RuntimeException("DB error")));

        Mono<Producto> result = adapter.modificarStock(1, 50);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().contains("Error al modificar el Stock de Producto"))
                .verify();
    }

    @Test
    void modificarStock_DeberiaNoEmitirSiNoExisteProducto() {
        when(repository.findById(99)).thenReturn(Mono.empty());

        Mono<Producto> result = adapter.modificarStock(99, 100);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void obtenerProductoMayorStock_DeberiaRetornarListaDeProductos() {
        when(repository.buscarProductosConMayorStock()).thenReturn(Flux.just(view));

        Flux<Producto> result = adapter.obtenerProductoMayorStock();

        StepVerifier.create(result)
                .expectNextMatches(p ->
                        p.getId() == 1 &&
                                p.getNombre().equals("Producto Test") &&
                                p.getStock() == 10 &&
                                p.getSucursalId() == 5
                )
                .verifyComplete();

        verify(repository).buscarProductosConMayorStock();
    }

    @Test
    void obtenerProductoMayorStock_DeberiaLanzarErrorSiFallaConsulta() {
        when(repository.buscarProductosConMayorStock()).thenReturn(Flux.error(new RuntimeException("DB error")));

        Flux<Producto> result = adapter.obtenerProductoMayorStock();

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().contains("Error al obtener Productos con mayor Stock"))
                .verify();
    }

}