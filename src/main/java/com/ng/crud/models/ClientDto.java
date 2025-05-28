package com.ng.crud.models;

import jakarta.validation.constraints.*;

// DTO = Data Transfer Object
//
// Recebe dados de formulários (criação ou edição).
// Envia dados para a view (exibição).
// Isola o domínio (entidade) do que é exposto para fora.

// O model padrão (geralmente uma @Entity) representa a estrutura real no banco de dados.
// O DTO é uma "versão paralela", mais controlada, usada para entrada/saída de dados — especialmente em formulários ou APIs.=

// Todo model tem seu MODEL e MODELDTO

// Por que usar DTO e não a entidade direto?
//
//    1. Separação de responsabilidades: não mistura lógica de banco com lógica de entrada de dados.
//
//    2. Validação customizada: você pode ter regras diferentes no DTO sem poluir a entidade.
//
//    3. Segurança: evita expor campos sensíveis ou desnecessários (ex: id, senha, dataCriacao).
//
//    4. Flexibilidade: permite montar formatos diferentes para exibição ou entrada, sem mexer no banco.

public class ClientDto {
    @NotEmpty(message = "The First Name is required")
    private String firstName;

    @NotEmpty(message = "The last Name is required")
    private String lastName;

    @NotEmpty(message = "The Email is required")
    @Email
    private String email;

    private String phone;
    private String address;

    @NotEmpty(message = "The Status is required")
    private String status;

    public @NotEmpty(message = "The First Name is required") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotEmpty(message = "The First Name is required") String firstName) {
        this.firstName = firstName;
    }

    public @NotEmpty(message = "The Status is required") String getStatus() {
        return status;
    }

    public void setStatus(@NotEmpty(message = "The Status is required") String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public @NotEmpty(message = "The Email is required") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "The Email is required") @Email String email) {
        this.email = email;
    }

    public @NotEmpty(message = "The last Name is required") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotEmpty(message = "The last Name is required") String lastName) {
        this.lastName = lastName;
    }
}
