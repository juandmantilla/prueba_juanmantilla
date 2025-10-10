package co.com.juan.mantilla.api.dtos;

import lombok.Builder;

@Builder
public record ProductoDTO(String nombre, Integer stock, String nombreSucursal) {
}
