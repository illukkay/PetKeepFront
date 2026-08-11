package com.petkeep.front.service;

import com.petkeep.front.model.Pet;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PetService {

private final RestClient restClient;

public PetService() {
    this.restClient = RestClient.builder()
         .baseUrl("http://localhost:3000")
         .build();
}

public Pet cadastrar(Pet pet) {

    return restClient.post()
        .uri("/pet/cadastro")
        .body(pet)
        .retrieve()
        .body(Pet.class);
}

public List<Pet> listarPorUsuario(Long usuarioId) {

    Pet[] pets = restClient.get()
         .uri("/pet/usuario/" + usuarioId)
         .retrieve()
         .body(Pet[].class);

    return Arrays.asList(pets);
}

public Pet buscarPorId(Long petId) {

    return restClient.get()
          .uri("/pet/" + petId)
          .retrieve()
          .body(Pet.class);
}

public Pet atualizar(Long petId, Long usuarioId, Pet pet) {

    return restClient.put()
           .uri("/pet/" + petId + "/" + usuarioId)
           .body(pet)
           .retrieve()
           .body(Pet.class);
}

public String excluir(Long petId, Long usuarioId) {

    return restClient.delete()
            .uri("/pet/" + petId + "/" + usuarioId)
            .retrieve()
            .body(String.class);
}
}
