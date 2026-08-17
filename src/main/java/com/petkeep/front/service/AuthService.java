package com.petkeep.front.service;

import com.petkeep.front.model.Prestador;
import com.petkeep.front.model.Usuario;
import java.util.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

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

    public String cadastrarPrestador(Prestador prestador, String token) {
        return restclient.post()
                .uri("/prestador/cadastro")
                .header("Authorization", "Bearer " + token)
                .body(prestador)
                .retrieve()
                .body(String.class);
    }

    public Usuario buscarUsuario(Long id) {
        return restclient.get()
                .uri("/user/" + id)
                .retrieve()
                .body(Usuario.class);
    }

    public Usuario usuarioDoToken(String token) {
        try {
            String[] partes = token.split("\\.");

            String payload = new String(
                    Base64.getUrlDecoder().decode(partes[1])
            );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(payload);

            Usuario usuario = new Usuario();

            usuario.setId(root.get("id").asLong());
            usuario.setNome(root.get("nome").asText());

            return usuario;

        } catch (Exception e) {
            throw new RuntimeException("Token inválido.");
        }
    }
}