package br.wesley.redis.controller;

import br.wesley.redis.dto.VeiculoDTO;
import br.wesley.redis.service.VeiculoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@Tag(name = "Veículos em Manutenção")
public class VeiculoController {

    private final VeiculoService service;

    @Autowired
    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{placa}")
    public ResponseEntity<VeiculoDTO> getByPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(service.findByPlaca(placa));
    }

    @PostMapping
    public ResponseEntity<VeiculoDTO> create(@RequestBody VeiculoDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{placa}")
    public ResponseEntity<VeiculoDTO> update(@PathVariable String placa, @RequestBody VeiculoDTO dto) {
        return ResponseEntity.ok(service.update(placa, dto));
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> delete(@PathVariable String placa) {
        service.deleteByPlaca(placa);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/limpar-cache")
    public ResponseEntity<Void> limparCache() {
        service.limparCache();
        return ResponseEntity.noContent().build();
    }
}
