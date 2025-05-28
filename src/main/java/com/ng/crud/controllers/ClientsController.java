package com.ng.crud.controllers;

import com.ng.crud.models.Client;
import com.ng.crud.models.ClientDto;
import com.ng.crud.repositories.IClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @GetMapping("/create")
    public String createClient(Model model){
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientDto", clientDto);

        return "clients/create";
    }

    @PostMapping("/create")
    public String createClient(
            @Valid @ModelAttribute ClientDto clientDto,
            BindingResult result) {

        if (clientRepo.findByEmail(clientDto.getEmail()) != null) {
            result.addError(
                    new FieldError("clientDto", "email", clientDto.getEmail(),
                            false, null, null, "Email address is already used")
            );
        }

        if (result.hasErrors()) {
            return "clients/create";
        }

        Client client = new Client();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setStatus(clientDto.getStatus());
        client.setCreatedAt(new Date());

        clientRepo.save(client);

        return "redirect:/clients";
    }
}
