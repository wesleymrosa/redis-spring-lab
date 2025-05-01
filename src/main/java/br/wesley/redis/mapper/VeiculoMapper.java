package br.wesley.redis.mapper;

import br.wesley.redis.dto.VeiculoDTO;
import br.wesley.redis.model.VeiculoManutencao;
import org.springframework.stereotype.Component;

@Component
public class VeiculoMapper {

    public VeiculoDTO toDTO(VeiculoManutencao veiculo) {
        return new VeiculoDTO(
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getOficina(),
                veiculo.getDataEntrega()
        );
    }

    public VeiculoManutencao toEntity(VeiculoDTO dto) {
        return new VeiculoManutencao(
                dto.getPlaca(),
                dto.getModelo(),
                dto.getOficina(),
                dto.getDataEntrega()
        );
    }
}
