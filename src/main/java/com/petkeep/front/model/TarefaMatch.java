package com.petkeep.front.model;

public class TarefaMatch {

    private Tarefa tarefa;
    private Double score;

    public TarefaMatch() {
    }

    public TarefaMatch(Tarefa tarefa, Double score) {
        this.tarefa = tarefa;
        this.score = score;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}