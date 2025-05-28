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

// Aqui no requestmapping declaramos que estamos navegando em clients agora, seja clients/create - clients/remove etc
// clients é a rota base para isso
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

    // Rota GET: exibe o formulário para criar novo cliente
    @GetMapping("/create")
    public String createClient(Model model) {
        // Cria um objeto ClientDto vazio para preencher o formulário
        ClientDto clientDto = new ClientDto();
        // Adiciona o DTO ao modelo para que o formulário possa acessar os campos
        model.addAttribute("clientDto", clientDto);
        // Retorna a view 'clients/create.html' para exibir o formulário
        return "clients/create";
    }

    // Rota POST: processa a submissão do formulário para criar cliente
    @PostMapping("/create")
    public String createClient(
            @Valid @ModelAttribute ClientDto clientDto, // Recebe os dados validados do formulário
            BindingResult result) {                     // Armazena erros de validação, se houver

        // Verifica se já existe cliente com o mesmo email no banco
        if (clientRepo.findByEmail(clientDto.getEmail()) != null) {
            // Adiciona erro manual para o campo email informando que já está em uso
            result.addError(new FieldError("clientDto", "email", clientDto.getEmail(),
                    false, null, null, "Email address is already used"));
        }

        // Se houver erros de validação, retorna para o formulário para correção
        if (result.hasErrors()) {
            return "clients/create";
        }

        // Cria nova entidade Client e preenche com dados do DTO

        /*
        Como o protocolo HTTP é stateless (não mantém estado entre requisições), o servidor não mantém o objeto
        entre o GET e o POST. Por isso, no GET, criamos/populamos um objeto (O clientDTO) para enviar à view e montar o
        formulário, e no POST (usando os dados do clientDto para preencher client), recebemos um objeto novo com os
        dados preenchidos que o usuário enviou. São instâncias separadas porque cada requisição HTTP é independente e
        isolada.
        */

        Client client = new Client();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setStatus(clientDto.getStatus());
        client.setCreatedAt(new Date()); // Define a data de criação atual

        // Salva o novo cliente no banco de dados
        clientRepo.save(client);

        // Após salvar, redireciona para a listagem de clientes
        return "redirect:/clients";
    }

    @GetMapping("/edit")
    // Exibe o formulário de edição para um client específico, buscando pelo id
    public String editClient(Model model, @RequestParam int id) {
        // Busca o client pelo id no banco, ou null se não existir
        Client client = clientRepo.findById(id).orElse(null);
        // Se não encontrar, redireciona para a lista de clients
        if (client == null) {
            return "redirect:/clients";
        }

        // Cria um ClientDto para popular o formulário de edição com os dados atuais
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setEmail(client.getEmail());
        clientDto.setPhone(client.getPhone());
        clientDto.setAddress(client.getAddress());
        clientDto.setStatus(client.getStatus());

        // Passa o client e o clientDto para a view preencher os campos
        model.addAttribute("client", client);
        model.addAttribute("clientDto", clientDto);

        return "clients/edit";  // Renderiza o template de edição
    }

    @PostMapping("/edit")
    // Recebe os dados do formulário editado para atualizar o client no banco
    public String editClient(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ClientDto clientDto,
            BindingResult result
    ) {
        // Busca o client pelo id, ou redireciona se não existir
        Client client = clientRepo.findById(id).orElse(null);
        if (client == null) {
            return "redirect:/clients";
        }

        // Mantém o client no model para o caso de erro retornar para o formulário
        model.addAttribute("client", client);

        // Se houver erros de validação, volta para o formulário sem salvar
        if (result.hasErrors()) {
            return "clients/edit";
        }

        // Atualiza o client com os dados do clientDto enviado pelo formulário
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setStatus(clientDto.getStatus());

        try {
            // Salva o client atualizado no banco
            clientRepo.save(client);
        } catch (Exception ex) {
            // Se salvar falhar (ex: email duplicado), adiciona erro e retorna o formulário
            result.addError(
                    new FieldError("clientDto", "email", clientDto.getEmail(),
                            false, null, null, "Email address is already used")
            );

            return "clients/edit";
        }

        // Se tudo certo, redireciona para a lista de clients
        return "redirect:/clients";
    }

    @GetMapping("/delete")
    // Deleta um client pelo id passado por parâmetro
    public String deleteClient(@RequestParam int id) {
        // Busca o client pelo id
        Client client = clientRepo.findById(id).orElse(null);

        // Se existir, deleta do banco
        if (client != null) {
            clientRepo.delete(client);
        }

        // Redireciona para a lista de clients após deletar (ou se não achar)
        return "redirect:/clients";
    }
}
