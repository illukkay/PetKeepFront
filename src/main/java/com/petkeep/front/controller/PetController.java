package com.petkeep.front.controller;

import com.petkeep.front.model.Pet;
import com.petkeep.front.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public String listarPets(Model model) {

        Long usuarioId = 1L;

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
            @ModelAttribute Pet pet) {
        pet.setTutor(
                new com.petkeep.front.model.Usuario()
        );
        pet.getTutor().setId(1L);
        petService.cadastrar(pet);
        return "redirect:/pets";
    }

    @GetMapping("/{petId}")
    public String detalhes(@PathVariable Long petId,Model model) {

        Pet pet = petService.buscarPorId(petId);

        model.addAttribute(
                "pet",
                pet
        );

        return "pet";
    }

}
