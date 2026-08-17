package com.petkeep.front.controller;

import com.petkeep.front.model.Avaliacao;
import com.petkeep.front.model.Tarefa;
import com.petkeep.front.model.Usuario;
import com.petkeep.front.service.AuthService;
import com.petkeep.front.service.AvaliacaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private AuthService auth;

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

    @GetMapping("/nova")
    public String paginaAvaliar(
            @RequestParam Long tarefaId,
            @RequestParam Long avaliadoId,
            @RequestParam(required = false) String avaliadoNome,
            @RequestParam(required = false) String petNome,
            HttpSession session,
            Model model) {

        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        model.addAttribute("tarefaId", tarefaId);
        model.addAttribute("avaliadoId", avaliadoId);
        model.addAttribute("avaliadoNome", avaliadoNome);
        model.addAttribute("petNome", petNome);

        return "avaliacao";
    }

    @PostMapping("/nova")
    public String avaliar(
            @RequestParam Long tarefaId,
            @RequestParam Long avaliadoId,
            @RequestParam Integer nota,
            @RequestParam(required = false) String comentario,
            @RequestParam(required = false) String avaliadoNome,
            @RequestParam(required = false) String petNome,
            HttpSession session,
            Model model) {

        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        try {
            Avaliacao avaliacao = new Avaliacao();

            Tarefa tarefa = new Tarefa();
            tarefa.setId(tarefaId);
            avaliacao.setTarefa(tarefa);

            Usuario avaliador = new Usuario();
            avaliador.setId(usuarioId);
            avaliacao.setAvaliador(avaliador);

            Usuario avaliado = new Usuario();
            avaliado.setId(avaliadoId);
            avaliacao.setAvaliado(avaliado);

            avaliacao.setNota(nota);
            avaliacao.setComentario(comentario);

            avaliacaoService.cadastrar(avaliacao);

            return "redirect:/tarefas";

        } catch (RestClientResponseException e) {
            model.addAttribute("errorMessage", extrairMensagemDeErro(e));
            model.addAttribute("tarefaId", tarefaId);
            model.addAttribute("avaliadoId", avaliadoId);
            model.addAttribute("avaliadoNome", avaliadoNome);
            model.addAttribute("petNome", petNome);

            return "avaliacao";
        }
    }
}
