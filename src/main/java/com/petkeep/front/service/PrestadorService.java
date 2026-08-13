package com.petkeep.front.service;

import com.petkeep.front.model.Prestador;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PrestadorService {

    private final RestClient restClient;

    public PrestadorService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:3000")
                .build();
    }

    public Prestador cadastrar(Prestador prestador, String token) {
        return restClient.post()
                .uri("/prestador/cadastro")
                .header("Authorization", "Bearer " + token)
                .body(prestador)
                .retrieve()
                .body(Prestador.class);
    }
}