package com.petkeep.front.service;

import com.petkeep.front.model.Tarefa;
import com.petkeep.front.model.TarefaMatch;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

@Service
public class TarefaService {

    private final RestClient restClient;

    public TarefaService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:3000")
                .build();
    }

    public Tarefa cadastrar(Tarefa tarefa) {
        return restClient.post()
                .uri("/tarefas/cadastro")
                .body(tarefa)
                .retrieve()
                .body(Tarefa.class);
    }

    public List<TarefaMatch> listarDisponiveis(Long usuarioId) {
        TarefaMatch[] tarefas = restClient.get()
                .uri("/tarefas/disponiveis/" + usuarioId)
                .retrieve()
                .body(TarefaMatch[].class);

        return Arrays.asList(tarefas);
    }

    public Tarefa aceitar(Long tarefaId, Long usuarioId) {
        return restClient.put()
                .uri("/tarefas/aceitar/" + tarefaId + "/" + usuarioId)
                .retrieve()
                .body(Tarefa.class);
    }

    public Tarefa concluir(Long tarefaId, Long prestadorId) {
        return restClient.put()
                .uri("/tarefas/concluir/" + tarefaId + "/" + prestadorId)
                .retrieve()
                .body(Tarefa.class);
    }
    @GetMapping
    public String tarefas(HttpSession session) {

    String token = (String) session.getAttribute("token");

    System.out.println("TOKEN NAS TAREFAS: " + token);

    if (token == null || token.isBlank()) {
        return "redirect:/logar";
    }

    return "tarefas";
}
}