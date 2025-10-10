package co.com.juan.mantilla.api;

import co.com.juan.mantilla.api.dtos.FranquiciaDTO;
import co.com.juan.mantilla.api.dtos.MensajeRespuestaDTO;
import co.com.juan.mantilla.api.dtos.ProductoDTO;
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
                .flatMap(saved -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(FranquiciaDTO.builder().nombre(saved.getNombre()).build()))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(
                        MensajeRespuestaDTO.builder()
                                .mensaje(e.getMessage()).build()

                ));
    }

    public Mono<ServerResponse> actualizarNombreFranquicia(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Franquicia.class)
                .flatMap(franquiciaUseCase::actualizarNombreFranquicia)
                .flatMap(updated -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(FranquiciaDTO.builder().nombre(updated.getNombre()).build()))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(MensajeRespuestaDTO.builder().mensaje(e.getMessage()).build()));
    }

    public Mono<ServerResponse> agregarProductoASucursal(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Producto.class)
                .flatMap(productoUseCase::agregarProductoASucursal)
                .flatMap(saved -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(ProductoDTO.builder().nombre(saved.getNombre()).stock(saved.getStock()).nombreSucursal(saved.getSucursalId().toString()).build()

                        ))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(MensajeRespuestaDTO.builder().mensaje(e.getMessage())));
    }

    public Mono<ServerResponse> obtenerProductoMayorStock(ServerRequest serverRequest) {

        var productos = productoUseCase.obtenerProductoMayorStock()
                .map(prod -> ProductoDTO.builder()
                        .nombre(prod.getNombre())
                        .stock(prod.getStock())
                        .nombreSucursal(prod.getSucursalId().toString())
                        .build());

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(productos, ProductoDTO.class)
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(MensajeRespuestaDTO.builder().mensaje(e.getMessage()).build()));
    }

    public Mono<ServerResponse> modificarStock(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Producto.class)
                .flatMap(productoUseCase::modificarStock)
                .flatMap(updated -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(updated))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(MensajeRespuestaDTO.builder().mensaje(e.getMessage()).build()));
    }

    public Mono<ServerResponse> agregarSucursalAFranquicia(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Sucursal.class)
                .flatMap(sucursalUseCase::agregarSucursalAFranquicia)
                .flatMap(saved -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(Sucursal.builder().nombre(saved.getNombre()))
                )
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(MensajeRespuestaDTO.builder().mensaje(e.getMessage()).build()));
    }

    public Mono<ServerResponse> actualizarNombreSucursal(ServerRequest serverRequest) {

        return serverRequest
                .bodyToMono(Sucursal.class)
                .flatMap(sucursalUseCase::actualizarNombreSucursal)
                .flatMap(updated -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(Sucursal.builder().nombre(updated.getNombre())
                        ))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(MensajeRespuestaDTO.builder().mensaje(e.getMessage()).build()));
    }

}
