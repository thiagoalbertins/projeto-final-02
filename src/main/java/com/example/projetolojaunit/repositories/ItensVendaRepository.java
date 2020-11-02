package com.example.projetolojaunit.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.example.projetolojaunit.entities.ItensVenda;
import com.example.projetolojaunit.entities.ItensVendaKey;

public interface ItensVendaRepository extends CrudRepository<ItensVenda, Integer>{

	List<ItensVenda> findByItensVendaKey(ItensVendaKey itensvendakey);
	
	@Transactional
	String deleteByItensVendaKey(ItensVendaKey itensvendakey);
	
	boolean existsByItensVendaKey(ItensVendaKey itensvendakey);
}
