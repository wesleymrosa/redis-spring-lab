package br.wesley.redis.mapper;

import br.wesley.redis.dto.VeiculoDTO;
import br.wesley.redis.model.VeiculoManutencao;
import org.springframework.stereotype.Component;

@Component
public class VeiculoMapper {

    public VeiculoDTO toDTO(VeiculoManutencao veiculo) {
        return VeiculoDTO.builder()
                .placa(veiculo.getPlaca())
                .modelo(veiculo.getModelo())
                .oficina(veiculo.getOficina())
                .dataEntrega(veiculo.getDataEntrega())
                .build();
    }

    public VeiculoManutencao toEntity(VeiculoDTO dto) {
        return VeiculoManutencao.builder()
                .placa(dto.getPlaca())
                .modelo(dto.getModelo())
                .oficina(dto.getOficina())
                .dataEntrega(dto.getDataEntrega())
                .build();
    }
}
