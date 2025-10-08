package co.com.juan.mantilla.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(PUT("/franquicia/agregarFranquicia"), handler::agregarFranquicia)
                .andRoute(POST("/franquicia/actualizarNombre"), handler::actualizarNombreFranquicia)
                .andRoute(PUT("/producto/agregarProducto"), handler::agregarProductoASucursal)
                .andRoute(POST("/producto/modificarStock"), handler::modificarStock)
                .andRoute(GET("/producto/mayorStock"), handler::obtenerProductoMayorStock)
                .andRoute(PUT("/sucursal/agregarSucursal"), handler::agregarSucursalAFranquicia).
                andRoute(POST("/sucursal/actualizarNombre"), handler::actualizarNombreSucursal);
    }
}
