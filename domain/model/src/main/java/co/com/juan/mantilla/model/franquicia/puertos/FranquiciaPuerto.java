package co.com.juan.mantilla.model.franquicia.puertos;

import co.com.juan.mantilla.model.franquicia.Franquicia;
import reactor.core.publisher.Mono;

public interface FranquiciaPuerto {

    Mono<Franquicia> agregarFranquicia(Franquicia franquicia);

    Mono<Franquicia> actualizarNombreFranquicia(String nombreFranquicia, Integer Id);
}
