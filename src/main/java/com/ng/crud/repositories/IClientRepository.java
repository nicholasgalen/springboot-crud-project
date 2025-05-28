package com.ng.crud.repositories;

import com.ng.crud.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface para usar o JpaRepo e seus metodos nos controllers para Client, também implementando findByEmail para não
// permitir repetição de email, ja que foi declarado como Unique no Client MODEL
public interface IClientRepository extends JpaRepository<Client, Integer> {

    public Client findByEmail(String email);
}
