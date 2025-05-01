package br.wesley.redis.dto;

import java.time.LocalDate;

public class VeiculoDTO {
    private String placa;
    private String modelo;
    private String oficina;
    private LocalDate dataEntrega;

    public VeiculoDTO() {
    }

    public VeiculoDTO(String placa, String modelo, String oficina, LocalDate dataEntrega) {
        this.placa = placa;
        this.modelo = modelo;
        this.oficina = oficina;
        this.dataEntrega = dataEntrega;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
}
