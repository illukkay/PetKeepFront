package com.petkeep.front.service;

import com.petkeep.front.model.Avaliacao;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AvaliacaoService {

    private final RestClient restClient;

    public AvaliacaoService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:3000")
                .build();
    }

    public Avaliacao cadastrar(Avaliacao avaliacao) {
        return restClient.post()
                .uri("/avaliacao/cadastro")
                .body(avaliacao)
                .retrieve()
                .body(Avaliacao.class);
    }
}
