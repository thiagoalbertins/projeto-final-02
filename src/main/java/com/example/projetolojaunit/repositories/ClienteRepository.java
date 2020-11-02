package com.example.projetolojaunit.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.projetolojaunit.entities.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer>{

	boolean existsByCpf(String cpf);
}
