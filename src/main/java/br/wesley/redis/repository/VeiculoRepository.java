package br.wesley.redis.repository;

import br.wesley.redis.model.VeiculoManutencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<VeiculoManutencao, Long> {
    Optional<VeiculoManutencao> findByPlaca(String placa);
}
