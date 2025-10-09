package co.com.juan.mantilla.r2dbc.sucursal;

import co.com.juan.mantilla.model.sucursal.Sucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class SucursalRepositoryAdapterTest {
    @Mock
    private SucursalRepository repository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private SucursalRepositoryAdapter adapter;

    private Sucursal sucursal;
    private SucursalEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sucursal = new Sucursal();
        sucursal.setId(1);
        sucursal.setNombre("Sucursal Principal");

        entity = new SucursalEntity();
        entity.setId(1);
        entity.setNombre("Sucursal Principal");
    }

    @Test
    void agregarSucursalAFranquicia_DeberiaGuardarCorrectamente() {
        when(mapper.map(sucursal, SucursalEntity.class)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.map(entity, Sucursal.class)).thenReturn(sucursal);

        Mono<Sucursal> result = adapter.agregarSucursalAFranquicia(sucursal);

        StepVerifier.create(result)
                .expectNext(sucursal)
                .verifyComplete();

        verify(repository, times(1)).save(entity);
    }

    @Test
    void agregarSucursalAFranquicia_DeberiaLanzarErrorAlFallar() {
        when(mapper.map(sucursal, SucursalEntity.class)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.error(new RuntimeException("DB error")));

        Mono<Sucursal> result = adapter.agregarSucursalAFranquicia(sucursal);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().contains("Error al intentar guardar la entidad Sucursal"))
                .verify();

        verify(repository).save(entity);
    }

    @Test
    void actualizarNombreSucursal_DeberiaActualizarCorrectamente() {
        String nuevoNombre = "Sucursal Norte";
        when(repository.findById(1)).thenReturn(Mono.just(entity));
        when(repository.save(any(SucursalEntity.class)))
                .thenAnswer(invocation -> {
                    SucursalEntity e = invocation.getArgument(0);
                    e.setNombre(nuevoNombre);
                    return Mono.just(e);
                });
        when(mapper.map(any(SucursalEntity.class), eq(Sucursal.class)))
                .thenAnswer(invocation -> {
                    SucursalEntity e = invocation.getArgument(0);
                    Sucursal s = new Sucursal();
                    s.setId(e.getId());
                    s.setNombre(e.getNombre());
                    return s;
                });

        Mono<Sucursal> result = adapter.actualizarNombreSucursal(nuevoNombre, 1);

        StepVerifier.create(result)
                .assertNext(s -> {
                    assertEquals(1, s.getId());
                    assertEquals(nuevoNombre, s.getNombre());
                })
                .verifyComplete();

        verify(repository).findById(1);
        verify(repository).save(any(SucursalEntity.class));
    }

    @Test
    void actualizarNombreSucursal_DeberiaNoEmitirSiNoExiste() {
        when(repository.findById(99)).thenReturn(Mono.empty());

        Mono<Sucursal> result = adapter.actualizarNombreSucursal("NuevaSucursal", 99);

        StepVerifier.create(result)
                .verifyComplete(); // no emite nada
    }

    @Test
    void actualizarNombreSucursal_DeberiaLanzarErrorAlFallar() {
        when(repository.findById(1)).thenReturn(Mono.error(new RuntimeException("DB error")));

        Mono<Sucursal> result = adapter.actualizarNombreSucursal("Sucursal Error", 1);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().contains("Error al actualizar la entidad Sucursal"))
                .verify();
    }

}