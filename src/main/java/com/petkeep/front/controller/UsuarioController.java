package com.petkeep.front.controller;

import com.petkeep.front.model.Prestador;
import com.petkeep.front.model.Usuario;
import com.petkeep.front.service.AuthService;
import com.petkeep.front.service.PrestadorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Controller
public class UsuarioController {

    @Autowired
    private AuthService auth;

    @Autowired
    private PrestadorService prestadorService;

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
        Object token = session.getAttribute("token");

        if (token == null) {
            return "redirect:/logar";
        }

        return "home";
    }

    @GetMapping("/logar")
public String PaginaLogar(HttpSession session, Model model) {
    model.addAttribute("user", new Usuario());

    String sucesso = (String) session.getAttribute("sucessoPrestador");

    if (sucesso != null) {
        model.addAttribute("successMessage", sucesso);
        session.removeAttribute("sucessoPrestador");
    }

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

    @GetMapping("/cadastro")
    public String paginaCadastro(Model model) {
        model.addAttribute("user", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute Usuario user, HttpSession session, Model model) {
        try {
            String token = auth.registrar(user);

            session.setAttribute("token", token);

            if (user.getTipoUsuario() == Usuario.TipoUsuario.PRESTADOR
                    || user.getTipoUsuario() == Usuario.TipoUsuario.AMBOS) {
                return "redirect:/cadastro-prestador";
            }

            return "redirect:/";

        } catch (HttpClientErrorException e) {
            String msg = extrairMensagemDeErro(e);

            model.addAttribute("errorMessage", msg);
            model.addAttribute("user", user);

            return "cadastro";
        }
    }

    @GetMapping("/cadastro-prestador")
    public String paginaCadastroPrestador(Model model) {
        model.addAttribute("prestador", new Prestador());
        return "cadastro-prestador";
    }

    @PostMapping("/cadastro-prestador")
public String cadastrarPrestador(@ModelAttribute Prestador prestador, HttpSession session) {
    String token = (String) session.getAttribute("token");

    if (token == null) {
        return "redirect:/logar";
    }

    Usuario usuario = auth.usuarioDoToken(token);

    prestador.setUsuario(usuario);

    auth.cadastrarPrestador(prestador, token);

    return "redirect:/";
}
}
