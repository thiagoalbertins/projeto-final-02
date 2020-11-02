package com.example.projetolojaunit.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.projetolojaunit.entities.Fornecedor;

public interface FornecedorRepository extends CrudRepository<Fornecedor, Integer>{

	boolean existsByCnpj(String cnpj);
}
