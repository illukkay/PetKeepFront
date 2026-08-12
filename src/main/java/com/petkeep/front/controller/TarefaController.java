package com.petkeep.front.controller;

import com.petkeep.front.model.Tarefa;
import com.petkeep.front.model.TarefaMatch;
import com.petkeep.front.service.TarefaService;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

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
            String[] partes = token.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(partes[1]));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(payload);

            return json.get("id").asLong();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/disponiveis")
    public String listarDisponiveis(HttpSession session, Model model) {
        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        List<TarefaMatch> tarefas = tarefaService.listarDisponiveis(usuarioId);
        model.addAttribute("tarefas", tarefas);

        return "tarefasdisp";
    }

    @GetMapping("/cadastro")
    public String paginaCadastro(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        return "cadastro-tarefa";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute Tarefa tarefa, HttpSession session) {
        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        tarefa.setTutor(new com.petkeep.front.model.Usuario());
        tarefa.getTutor().setId(usuarioId);

        tarefaService.cadastrar(tarefa);

        return "redirect:/tarefas/disponiveis";
    }

    @PostMapping("/aceitar/{tarefaId}")
    public String aceitar(@PathVariable Long tarefaId, HttpSession session) {
        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        tarefaService.aceitar(tarefaId, usuarioId);

        return "redirect:/tarefas/disponiveis";
    }

    @PostMapping("/concluir/{tarefaId}")
    public String concluir(@PathVariable Long tarefaId, HttpSession session) {
        Long usuarioId = pegarUsuarioId(session);

        if (usuarioId == null) {
            return "redirect:/logar";
        }

        tarefaService.concluir(tarefaId, usuarioId);

        return "redirect:/tarefas/disponiveis";
    }
}