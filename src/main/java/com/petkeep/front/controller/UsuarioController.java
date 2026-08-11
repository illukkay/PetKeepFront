/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.petkeep.front.controller;

import com.petkeep.front.model.Usuario;
import com.petkeep.front.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Controller
public class UsuarioController {

    @Autowired
    private AuthService auth;

    private String extrairMensagemDeErro(HttpClientErrorException e) {
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

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Object token = (String) session.getAttribute("token");

        if (token == null) {
            return "redirect:/logar";
        }
        return "home";
    }

    @GetMapping("/logar")
    public String PaginaLogar(Model model) {
        model.addAttribute("user", new Usuario());
        return "logar";
    }

    @PostMapping("/logar")
    public String fazerLogin(@ModelAttribute Usuario user, HttpSession session, Model model) {
        try {
            String token = auth.logar(user);
            session.setAttribute("token", token);
            return "redirect:/";
        } catch (HttpClientErrorException e) {
            String msg = extrairMensagemDeErro(e);
            model.addAttribute("errorMessage", msg);
            model.addAttribute("user", user);
            return "logar";
        }
    }
    
}
