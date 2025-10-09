package co.com.juan.mantilla.api;

import co.com.juan.mantilla.api.dtos.MensajeRespuestaDTO;
import co.com.juan.mantilla.model.franquicia.Franquicia;
import co.com.juan.mantilla.model.producto.Producto;
import co.com.juan.mantilla.model.sucursal.Sucursal;
import co.com.juan.mantilla.usecase.FranquiciaCasoUso;
import co.com.juan.mantilla.usecase.ProductoCasoUso;
import co.com.juan.mantilla.usecase.SucursalCasoUso;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class Handler {

    private final FranquiciaCasoUso franquiciaCasoUso;
    private final ProductoCasoUso productoCasoUso;
    private final SucursalCasoUso sucursalCasoUso;

    public Mono<ServerResponse> agregarFranquicia(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Franquicia.class)
                .flatMap(franquiciaCasoUso::agregarFranquicia)
                .flatMap(saved -> ServerResponse.ok().contentType(APPLICATION_JSON)
                        .bodyValue(saved))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al momento de agregar la Franquicia :" + e.getMessage()));
    }

    public Mono<ServerResponse> actualizarNombreFranquicia(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Franquicia.class)
                .flatMap(franquiciaCasoUso::actualizarNombreFranquicia)
                .flatMap(updated -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(updated))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al momento de actualizar el nombre de la franquicia :" + e.getMessage()));
    }

    public Mono<ServerResponse> agregarProductoASucursal(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Producto.class)
                .flatMap(productoCasoUso::agregarProductoASucursal)
                .flatMap(saved -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(saved))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(MensajeRespuestaDTO.builder().mensaje(e.getMessage())));
    }

    public Mono<ServerResponse> obtenerProductoMayorStock(ServerRequest serverRequest) {

        var productos = productoCasoUso.obtenerProductoMayorStock();

        return ServerResponse.ok().contentType(APPLICATION_JSON).body(productos, Producto.class);
    }

    public Mono<ServerResponse> modificarStock(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Producto.class)
                .flatMap(productoCasoUso::modificarStock)
                .flatMap(updated -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(updated))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al momento de modificar el Stock de Producto :" + e.getMessage()));
    }

    public Mono<ServerResponse> agregarSucursalAFranquicia(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Sucursal.class)
                .flatMap(sucursalCasoUso::agregarSucursalAFranquicia)
                .flatMap(saved -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(saved))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al momento de agregar una Sucursal : " + e.getMessage()));
    }

    public Mono<ServerResponse> actualizarNombreSucursal(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Sucursal.class)
                .flatMap(sucursalCasoUso::actualizarNombreSucursal)
                .flatMap(saved -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(saved))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al momento de actualizar el nombre de la Sucursal: " + e.getMessage()));
    }

}
