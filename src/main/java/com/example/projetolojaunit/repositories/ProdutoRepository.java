package com.example.projetolojaunit.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.projetolojaunit.entities.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{

}
