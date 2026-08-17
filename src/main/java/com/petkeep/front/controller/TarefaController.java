package com.petkeep.front.controller;

import com.petkeep.front.model.Tarefa;
import com.petkeep.front.model.TarefaMatch;
import com.petkeep.front.service.AuthService;
import com.petkeep.front.service.PetService;
import com.petkeep.front.service.TarefaService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private PetService petService;

    @Autowired
    private AuthService auth;

    @GetMapping
    public String tarefas() {
    return "tarefas";
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

    @GetMapping("/disponiveis")
    public String listarDisponiveis(HttpSession session, Model model) {
        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        try {
            List<TarefaMatch> tarefas = tarefaService.listarDisponiveis(usuarioId);
            model.addAttribute("tarefas", tarefas);
        } catch (RestClientResponseException e) {
            model.addAttribute("tarefas", java.util.Collections.emptyList());
            model.addAttribute("errorMessage", extrairMensagemDeErro(e));
        }

        return "tarefasdisp";
    }

    @GetMapping("/minhas")
    public String listarMinhas(HttpSession session, Model model) {
        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        model.addAttribute("tarefas", tarefaService.listarPorTutor(usuarioId));

        return "minhastarefas";
    }

    @GetMapping("/andamento")
    public String listarAndamento(HttpSession session, Model model) {
        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        try {
            model.addAttribute("tarefas", tarefaService.listarEmAndamento(usuarioId));
        } catch (RestClientResponseException e) {
            model.addAttribute("tarefas", java.util.Collections.emptyList());
            model.addAttribute("errorMessage", extrairMensagemDeErro(e));
        }

        model.addAttribute("usuarioId", usuarioId);

        return "tarefasandamento";
    }

    @GetMapping("/cadastro")
    public String paginaCadastro(HttpSession session, Model model) {
        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        model.addAttribute("tarefa", new Tarefa());
        model.addAttribute("pets", petService.listarPorUsuario(usuarioId));

        return "cadastrotarefa";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute Tarefa tarefa, HttpSession session, Model model) {
        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        tarefa.setTutor(new com.petkeep.front.model.Usuario());
        tarefa.getTutor().setId(usuarioId);

        try {
            tarefaService.cadastrar(tarefa);

            return "redirect:/tarefas/minhas";

        } catch (RestClientResponseException e) {
            model.addAttribute("errorMessage", extrairMensagemDeErro(e));
            model.addAttribute("tarefa", tarefa);
            model.addAttribute("pets", petService.listarPorUsuario(usuarioId));

            return "cadastrotarefa";
        }
    }

    @PostMapping("/aceitar/{tarefaId}")
    public String aceitar(@PathVariable Long tarefaId, HttpSession session, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        try {
            tarefaService.aceitar(tarefaId, usuarioId);
        } catch (RestClientResponseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", extrairMensagemDeErro(e));
        }

        return "redirect:/tarefas/disponiveis";
    }

    @PostMapping("/concluir/{tarefaId}")
    public String concluir(@PathVariable Long tarefaId, HttpSession session, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        try {
            Tarefa tarefa = tarefaService.concluir(tarefaId, usuarioId);

            String nomeTutor = java.net.URLEncoder.encode(tarefa.getTutor().getNome(), java.nio.charset.StandardCharsets.UTF_8);
            String nomePet = java.net.URLEncoder.encode(tarefa.getPet().getNome(), java.nio.charset.StandardCharsets.UTF_8);

            return "redirect:/avaliacao/nova?tarefaId=" + tarefa.getId()
                    + "&avaliadoId=" + tarefa.getTutor().getId()
                    + "&avaliadoNome=" + nomeTutor
                    + "&petNome=" + nomePet;

        } catch (RestClientResponseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", extrairMensagemDeErro(e));

            return "redirect:/tarefas/andamento";
        }
    }
}