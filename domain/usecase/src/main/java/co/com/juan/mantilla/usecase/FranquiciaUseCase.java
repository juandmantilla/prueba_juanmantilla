package co.com.juan.mantilla.usecase;

import co.com.juan.mantilla.model.franquicia.Franquicia;
import co.com.juan.mantilla.model.franquicia.gateways.FranquiciaGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranquiciaUseCase {

    private final FranquiciaGateway repository;

    public Mono<Franquicia> agregarFranquicia(Franquicia franquicia) {
        return repository.agregarFranquicia(franquicia);
    }

    public Mono<Franquicia> actualizarNombreFranquicia(Franquicia franquicia) {
        return repository.actualizarNombreFranquicia(franquicia.getNombre(), franquicia.getId());
    }

}
