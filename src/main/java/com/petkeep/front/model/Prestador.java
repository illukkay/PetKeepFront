package com.petkeep.front.model;

public class Prestador {
    private Long id;
    private Usuario usuario;
    private Boolean aceitaHospedagem;
    private Boolean aceitaPasseio;
    private Boolean aceitaBanho;
    private Boolean aceitaPequeno;
    private Boolean aceitaMedio;
    private Boolean aceitaGrande;
    private Boolean aceitaGigante;
    private String descricao;
    private Double valorHora;

    public Prestador() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getAceitaHospedagem() {
        return aceitaHospedagem;
    }

    public void setAceitaHospedagem(Boolean aceitaHospedagem) {
        this.aceitaHospedagem = aceitaHospedagem;
    }

    public Boolean getAceitaPasseio() {
        return aceitaPasseio;
    }

    public void setAceitaPasseio(Boolean aceitaPasseio) {
        this.aceitaPasseio = aceitaPasseio;
    }

    public Boolean getAceitaBanho() {
        return aceitaBanho;
    }

    public void setAceitaBanho(Boolean aceitaBanho) {
        this.aceitaBanho = aceitaBanho;
    }

    public Boolean getAceitaPequeno() {
        return aceitaPequeno;
    }

    public void setAceitaPequeno(Boolean aceitaPequeno) {
        this.aceitaPequeno = aceitaPequeno;
    }

    public Boolean getAceitaMedio() {
        return aceitaMedio;
    }

    public void setAceitaMedio(Boolean aceitaMedio) {
        this.aceitaMedio = aceitaMedio;
    }

    public Boolean getAceitaGrande() {
        return aceitaGrande;
    }

    public void setAceitaGrande(Boolean aceitaGrande) {
        this.aceitaGrande = aceitaGrande;
    }

    public Boolean getAceitaGigante() {
        return aceitaGigante;
    }

    public void setAceitaGigante(Boolean aceitaGigante) {
        this.aceitaGigante = aceitaGigante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorHora() {
        return valorHora;
    }

    public void setValorHora(Double valorHora) {
        this.valorHora = valorHora;
    }
}