package co.com.juan.mantilla.api;

import co.com.juan.mantilla.model.franquicia.Franquicia;
import co.com.juan.mantilla.model.producto.Producto;
import co.com.juan.mantilla.model.sucursal.Sucursal;
import co.com.juan.mantilla.usecase.FranquiciaUseCase;
import co.com.juan.mantilla.usecase.ProductoUseCase;
import co.com.juan.mantilla.usecase.SucursalUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class Handler {

    private final FranquiciaUseCase franquiciaUseCase;
    private final ProductoUseCase productoUseCase;
    private final SucursalUseCase sucursalUseCase;

    public Mono<ServerResponse> agregarFranquicia(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Franquicia.class)
                .flatMap(franquiciaUseCase::agregarFranquicia)
                .flatMap(saved -> ServerResponse.ok().contentType(APPLICATION_JSON)
                        .bodyValue(saved))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al momento de agregar la Franquicia :" + e.getMessage()));
    }

    public Mono<ServerResponse> actualizarNombreFranquicia(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Franquicia.class)
                .flatMap(franquiciaUseCase::actualizarNombreFranquicia)
                .flatMap(updated -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(updated))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al momento de actualizar el nombre de la franquicia :" + e.getMessage()));
    }

    public Mono<ServerResponse> agregarProductoASucursal(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Producto.class)
                .flatMap(productoUseCase::agregarProductoASucursal)
                .flatMap(saved -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(saved))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al momento de agregar Producto a Sucursal :" + e.getMessage()));
    }

    public Mono<ServerResponse> modificarStock(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Producto.class)
                .flatMap(productoUseCase::modificarStock)
                .flatMap(updated -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(updated))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al momento de modificar el Stock de Producto :" + e.getMessage()));
    }

    public Mono<ServerResponse> agregarSucursalAFranquicia(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Sucursal.class)
                .flatMap(sucursalUseCase::agregarSucursalAFranquicia)
                .flatMap(saved -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(saved))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al momento de agregar una Sucursal : " + e.getMessage()));
    }

    public Mono<ServerResponse> actualizarNombreSucursal(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Sucursal.class)
                .flatMap(sucursalUseCase::actualizarNombreSucursal)
                .flatMap(saved -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(saved))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al momento de actualizar el nombre de la Sucursal: " + e.getMessage()));
    }

}
