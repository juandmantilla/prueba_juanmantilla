package co.com.juan.mantilla.usecase;

import co.com.juan.mantilla.model.franquicia.Franquicia;
import co.com.juan.mantilla.model.franquicia.gateways.FranquiciaGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class FranquiciaUseCase {

    private final FranquiciaGateway repository;

    public Mono<Franquicia> agregarFranquicia(Franquicia franquicia) {
        log.info("Ejecutando caso de uso de Agregar Franquicia");
        return repository.agregarFranquicia(franquicia);
    }

    public Mono<Franquicia> actualizarNombreFranquicia(Franquicia franquicia) {
        log.info("Ejecutando caso de uso Actualizar Nombre Franquicia");
        return repository.actualizarNombreFranquicia(franquicia.getNombre(), franquicia.getId());
    }

}
