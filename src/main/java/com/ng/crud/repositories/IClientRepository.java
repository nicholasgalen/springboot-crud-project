package com.ng.crud.repositories;

import com.ng.crud.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientRepository extends JpaRepository<Client, Integer> {

    public Client findByEmail(String email);
}
