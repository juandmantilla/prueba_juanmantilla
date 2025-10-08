package co.com.juan.mantilla.usecase;

import co.com.juan.mantilla.model.sucursal.Sucursal;
import co.com.juan.mantilla.model.sucursal.gateways.SucursalGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SucursalUseCase {

    private final SucursalGateway repository;

    public Mono<Sucursal> agregarSucursalAFranquicia(Sucursal sucursal) {
        return repository.agregarSucursalAFranquicia(sucursal);
    }

    public Mono<Sucursal> actualizarNombreSucursal(Sucursal sucursal) {
        return repository.actualizarNombreSucursal(sucursal.getNombre(), sucursal.getId());
    }
}
