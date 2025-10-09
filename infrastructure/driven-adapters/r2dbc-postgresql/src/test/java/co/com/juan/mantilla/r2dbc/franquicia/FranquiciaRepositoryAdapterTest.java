package co.com.juan.mantilla.r2dbc.franquicia;

import co.com.juan.mantilla.model.franquicia.Franquicia;
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


class FranquiciaRepositoryAdapterTest {

    @Mock
    private FranquiciaRepository repository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private FranquiciaRepositoryAdapter adapter;

    private Franquicia franquicia;
    private FranquiciaEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        franquicia = new Franquicia();
        franquicia.setId(1);
        franquicia.setNombre("Franquicia Test");

        entity = new FranquiciaEntity();
        entity.setId(1);
        entity.setNombre("Franquicia Test");
    }

    @Test
    void agregarFranquicia_DeberiaGuardarYDevolverEntidad() {

        when(mapper.map(franquicia, FranquiciaEntity.class)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.map(entity, Franquicia.class)).thenReturn(franquicia);


        Mono<Franquicia> result = adapter.agregarFranquicia(franquicia);

        StepVerifier.create(result)
                .expectNext(franquicia)
                .verifyComplete();

        verify(repository, times(1)).save(entity);
    }

    @Test
    void agregarFranquicia_DeberiaRetornarErrorSiFallaGuardado() {

        when(mapper.map(franquicia, FranquiciaEntity.class)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.error(new RuntimeException("DB error")));

        Mono<Franquicia> result = adapter.agregarFranquicia(franquicia);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().contains("No se pudo guardar la entidad Franquicia"))
                .verify();
    }

    @Test
    void actualizarNombreFranquicia_DeberiaActualizarCorrectamente() {

        String nuevoNombre = "Franquicia Actualizada";
        when(repository.findById(1)).thenReturn(Mono.just(entity));
        when(repository.save(any(FranquiciaEntity.class))).thenAnswer(invocation -> {
            FranquiciaEntity e = invocation.getArgument(0);
            e.setNombre(nuevoNombre);
            return Mono.just(e);
        });
        when(mapper.map(any(FranquiciaEntity.class), eq(Franquicia.class)))
                .thenAnswer(invocation -> {
                    FranquiciaEntity e = invocation.getArgument(0);
                    Franquicia f = new Franquicia();
                    f.setId(e.getId());
                    f.setNombre(e.getNombre());
                    return f;
                });

        Mono<Franquicia> result = adapter.actualizarNombreFranquicia(nuevoNombre, 1);

        StepVerifier.create(result)
                .assertNext(f -> {
                    assertEquals(nuevoNombre, f.getNombre());
                    assertEquals(1, f.getId());
                })
                .verifyComplete();

        verify(repository).findById(1);
        verify(repository).save(any(FranquiciaEntity.class));
    }

    @Test
    void actualizarNombreFranquicia_DeberiaRetornarErrorSiNoExiste() {

        when(repository.findById(99)).thenReturn(Mono.empty());


        Mono<Franquicia> result = adapter.actualizarNombreFranquicia("NuevoNombre", 99);


        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void actualizarNombreFranquicia_DeberiaRetornarErrorSiFallaEnRepositorio() {

        when(repository.findById(1)).thenReturn(Mono.error(new RuntimeException("DB error")));

        Mono<Franquicia> result = adapter.actualizarNombreFranquicia("NuevoNombre", 1);

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().contains("No se pudo actualizar la entidad Franquicia"))
                .verify();
    }

}