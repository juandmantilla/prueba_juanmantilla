package co.com.juan.mantilla.api.dtos;

import lombok.Builder;

@Builder
public record SucursalDTO(String nombre, Integer franquiciaId) {
}
