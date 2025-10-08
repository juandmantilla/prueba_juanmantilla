package co.com.juan.mantilla.model.sucursal.gateways;

import co.com.juan.mantilla.model.sucursal.Sucursal;
import reactor.core.publisher.Mono;

public interface SucursalGateway {

    Mono<Sucursal> agregarSucursalAFranquicia(Sucursal sucursal);

    Mono<Sucursal> actualizarNombreSucursal(String nombreSucursal, Integer id);
}
