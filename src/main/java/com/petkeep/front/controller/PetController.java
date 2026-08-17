package com.petkeep.front.controller;

import com.petkeep.front.model.Pet;
import com.petkeep.front.service.AuthService;
import com.petkeep.front.service.PetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private AuthService auth;

    private String extrairMensagemDeErro(RestClientResponseException e) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(e.getResponseBodyAsString());

            if (root.has("message")) {
                return root.get("message").asText();
            }
        } catch (Exception ex) {
        }

        return "Ocorreu um erro inesperado na comunicação.";
    }

    private Long pegarUsuarioId(HttpSession session) {
        String token = (String) session.getAttribute("token");

        if (token == null) {
            return null;
        }

        try {
            return auth.usuarioDoToken(token).getId();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping
    public String listarPets(HttpSession session, Model model) {

        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        model.addAttribute(
                "pets",
                petService.listarPorUsuario(usuarioId)
        );

        return "pets";
    }

    @GetMapping("/cadastro")
    public String paginaCadastro(Model model) {

        model.addAttribute(
                "pet",
                new Pet()
        );

        return "cadastro-pet";
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            @ModelAttribute Pet pet,
            HttpSession session,
            Model model) {

        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        pet.setTutor(
                new com.petkeep.front.model.Usuario()
        );

        pet.getTutor().setId(usuarioId);

        try {
            petService.cadastrar(pet);

            return "redirect:/pets";

        } catch (RestClientResponseException e) {
            model.addAttribute("errorMessage", extrairMensagemDeErro(e));
            model.addAttribute("pet", pet);

            return "cadastro-pet";
        }
    }

    @GetMapping("/{petId}")
    public String detalhes(
            @PathVariable Long petId,
            Model model) {

        Pet pet = petService.buscarPorId(petId);

        model.addAttribute(
                "pet",
                pet
        );

        return "pet";
    }
}