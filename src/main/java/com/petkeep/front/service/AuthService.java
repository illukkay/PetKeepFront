package com.petkeep.front.service;

import com.petkeep.front.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthService {
private final RestClient restclient;

public AuthService() {
    this.restclient = RestClient.builder()
            .baseUrl("http://localhost:3000")
            .build();
}

public String logar(Usuario user) {
    return restclient.post()
            .uri("/user/logar")
            .body(user)
            .retrieve()
            .body(String.class);
}

public String registrar(Usuario user) {
    return restclient.post()
            .uri("/user/registro")
            .body(user)
            .retrieve()
            .body(String.class);
}
}