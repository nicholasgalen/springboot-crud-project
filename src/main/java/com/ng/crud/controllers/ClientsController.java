package com.ng.crud.controllers;

import com.ng.crud.repositories.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientsController {
    // Chamamos o IClientRepository para fazer um dependency injection do JpaRepository que usamos extend na interface
    // Autowired é uma anotação para avisar o spring para injetar essa dependencia
    @Autowired
    private IClientRepository clientRepo;

    @GetMapping({"", "/"})
    // Declaramos um model da interface Model para usar o metodo addAtribute
    public String getClients(Model model) {
        // usamos clientRepo do JpaRepository para usar metodos prontos como o findAll e Sort etc
        var clients = clientRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        // Aqui declaramos o nome do atributo clients (variavel criada acima) como clients (que contém todos os valores
        // de clients como id, name etc) para enviar clients do MODEL para a VIEW
        model.addAttribute("clients", clients);

        return "clients/index";
    }
}
