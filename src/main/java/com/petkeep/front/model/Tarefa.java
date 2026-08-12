package com.petkeep.front.model;

import java.time.LocalDateTime;

public class Tarefa {

    private Long id;
    private Usuario tutor;
    private Usuario prestador;
    private Pet pet;
    private TipoServico tipoServico;
    private Status status;
    private Double valor;
    private LocalDateTime dataServico;
    private String descricao;

    public enum TipoServico {
        PASSEIO,
        BANHO,
        HOSPEDAGEM,
        CUIDADO_DOMICILIAR
    }

    public enum Status {
        ABERTA,
        EM_ANDAMENTO,
        CONCLUIDA,
        CANCELADA
    }

    public Tarefa() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getTutor() {
        return tutor;
    }

    public void setTutor(Usuario tutor) {
        this.tutor = tutor;
    }

    public Usuario getPrestador() {
        return prestador;
    }

    public void setPrestador(Usuario prestador) {
        this.prestador = prestador;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataServico() {
        return dataServico;
    }

    public void setDataServico(LocalDateTime dataServico) {
        this.dataServico = dataServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}