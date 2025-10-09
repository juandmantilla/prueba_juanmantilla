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
        return route(PUT("/franquicia"), handler::agregarFranquicia)
                .andRoute(POST("/franquicia"), handler::actualizarNombreFranquicia)
                .andRoute(PUT("/producto"), handler::agregarProductoASucursal)
                .andRoute(POST("/producto"), handler::modificarStock)
                .andRoute(GET("/producto"), handler::obtenerProductoMayorStock)
                .andRoute(PUT("/sucursal"), handler::agregarSucursalAFranquicia).
                andRoute(POST("/sucursal"), handler::actualizarNombreSucursal);
    }
}
