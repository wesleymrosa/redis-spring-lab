package br.wesley.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoDTO {
    private String placa;
    private String modelo;
    private String oficina;
    private LocalDate dataEntrega;
}
