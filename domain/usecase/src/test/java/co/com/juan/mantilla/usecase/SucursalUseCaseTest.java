package co.com.juan.mantilla.usecase;

import co.com.juan.mantilla.model.sucursal.Sucursal;
import co.com.juan.mantilla.model.sucursal.puertos.SucursalPuerto;
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


class SucursalUseCaseTest {

    private SucursalPuerto puerto;
    private SucursalUseCase sucursalUseCase;

    @BeforeEach
    void setUp() {
        puerto = Mockito.mock(SucursalPuerto.class);
        sucursalUseCase = new SucursalUseCase(puerto);
    }

    @Test
    void debeAgregarSucursalAFranquicia() {

        Sucursal sucursal = new Sucursal(1, "Sucursal Norte", 10);
        when(puerto.agregarSucursalAFranquicia(any(Sucursal.class))).thenReturn(Mono.just(sucursal));

        StepVerifier.create(sucursalUseCase.agregarSucursalAFranquicia(sucursal))
                .expectNext(sucursal)
                .verifyComplete();

        verify(puerto, times(1)).agregarSucursalAFranquicia(any(Sucursal.class));
    }

    @Test
    void debeActualizarNombreSucursal() {

        Sucursal sucursal = new Sucursal(1, "Sucursal Actualizada", 5);
        when(puerto.actualizarNombreSucursal(eq("Sucursal Actualizada"), eq(1)))
                .thenReturn(Mono.just(sucursal));

        StepVerifier.create(sucursalUseCase.actualizarNombreSucursal(sucursal))
                .expectNext(sucursal)
                .verifyComplete();

        verify(puerto, times(1)).actualizarNombreSucursal(eq("Sucursal Actualizada"), eq(1));
    }

    @Test
    void debePropagarErrorSiFallaElRepositorio() {

        Sucursal sucursal = new Sucursal(2, "Sucursal Error", 3);
        when(puerto.agregarSucursalAFranquicia(any())).thenReturn(Mono.error(new RuntimeException("Error al guardar sucursal")));


        StepVerifier.create(sucursalUseCase.agregarSucursalAFranquicia(sucursal))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Error al guardar sucursal"))
                .verify();

        verify(puerto, times(1)).agregarSucursalAFranquicia(any());
    }

}