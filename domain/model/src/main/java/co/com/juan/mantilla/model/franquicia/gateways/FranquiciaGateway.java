package co.com.juan.mantilla.model.franquicia.gateways;

import co.com.juan.mantilla.model.franquicia.Franquicia;
import reactor.core.publisher.Mono;

public interface FranquiciaGateway {

    Mono<Franquicia> agregarFranquicia(Franquicia franquicia);

    Mono<Franquicia> actualizarNombreFranquicia(String nombreFranquicia, Integer Id);
}
