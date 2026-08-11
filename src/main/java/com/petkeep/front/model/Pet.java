/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.petkeep.front.model;

public class Pet {
    private Long id;
    private Usuario tutor;
    private String nome;
    private Especie especie;
    private String raca;
    private Integer idade;
    private Porte porte;
    private String observacoes;
    public enum Especie {
        CACHORRO,
        GATO,
        PASSARO,
        CALOPSITA,
        PERIQUITO,
        PAPAGAIO,
        COELHO,
        HAMSTER,
        PORQUINHO_DA_INDIA,
        CHINCHILA,
        FURAO,
        TARTARUGA,
        CAGADO,
        IGUANA,
        LAGARTO,
        COBRA,
        PEIXE,
        MINI_PIG,
        CAVALO,
        JABUTI
    }

    public enum Porte {
        PEQUENO,
        MEDIO,
        GRANDE,
        GIGANTE
    }

    public Pet() {
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

}
