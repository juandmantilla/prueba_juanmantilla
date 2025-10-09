package co.com.juan.mantilla.model.sucursal.puertos;

import co.com.juan.mantilla.model.sucursal.Sucursal;
import reactor.core.publisher.Mono;

public interface SucursalPuerto {

    Mono<Sucursal> agregarSucursalAFranquicia(Sucursal sucursal);

    Mono<Sucursal> actualizarNombreSucursal(String nombreSucursal, Integer id);
}
