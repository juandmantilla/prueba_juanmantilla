package co.com.juan.mantilla.usecase;

import co.com.juan.mantilla.model.franquicia.Franquicia;
import co.com.juan.mantilla.model.franquicia.puertos.FranquiciaPuerto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class FranquiciaUseCaseTest {
    private FranquiciaPuerto puerto;
    private FranquiciaUseCase franquiciaUseCase;

    @BeforeEach
    void setUp() {
        puerto = Mockito.mock(FranquiciaPuerto.class);
        franquiciaUseCase = new FranquiciaUseCase(puerto);
    }

    @Test
    void debeAgregarFranquicia() {

        Franquicia franquicia = new Franquicia(1, "Franquicia Central");
        when(puerto.agregarFranquicia(any(Franquicia.class))).thenReturn(Mono.just(franquicia));

        StepVerifier.create(franquiciaUseCase.agregarFranquicia(franquicia))
                .expectNext(franquicia)
                .verifyComplete();

        verify(puerto, times(1)).agregarFranquicia(any(Franquicia.class));
    }

    @Test
    void debeActualizarNombreFranquicia() {

        Franquicia franquicia = new Franquicia(1, "Nueva Franquicia");
        when(puerto.actualizarNombreFranquicia(eq("Nueva Franquicia"), eq(1))).thenReturn(Mono.just(franquicia));

        StepVerifier.create(franquiciaUseCase.actualizarNombreFranquicia(franquicia))
                .expectNext(franquicia)
                .verifyComplete();

        verify(puerto, times(1)).actualizarNombreFranquicia(eq("Nueva Franquicia"), eq(1));
    }

    @Test
    void debePropagarErrorSiFallaElRepositorio() {

        Franquicia franquicia = new Franquicia(1, "Franquicia Error");
        when(puerto.agregarFranquicia(any())).thenReturn(Mono.error(new RuntimeException("Error al guardar franquicia")));

        StepVerifier.create(franquiciaUseCase.agregarFranquicia(franquicia))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Error al guardar franquicia"))
                .verify();

        verify(puerto, times(1)).agregarFranquicia(any());
    }
}