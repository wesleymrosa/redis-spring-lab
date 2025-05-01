package br.wesley.redis.service;

import br.wesley.redis.dto.VeiculoDTO;
import br.wesley.redis.mapper.VeiculoMapper;
import br.wesley.redis.model.VeiculoManutencao;
import br.wesley.redis.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository repository;
    private final VeiculoMapper mapper;

    public VeiculoService(VeiculoRepository repository, VeiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Cacheable("veiculos")
    public List<VeiculoDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "veiculoByPlaca", key = "#placa")
    public VeiculoDTO findByPlaca(String placa) {
        VeiculoManutencao v = repository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        return mapper.toDTO(v);
    }

    @CachePut(value = "veiculoByPlaca", key = "#dto.placa")
    public VeiculoDTO create(VeiculoDTO dto) {
        VeiculoManutencao veiculo = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(veiculo));
    }

    @CachePut(value = "veiculoByPlaca", key = "#placa")
    public VeiculoDTO update(String placa, VeiculoDTO dto) {
        VeiculoManutencao existente = repository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        existente.setModelo(dto.getModelo());
        existente.setOficina(dto.getOficina());
        existente.setDataEntrega(dto.getDataEntrega());

        return mapper.toDTO(repository.save(existente));
    }
}
