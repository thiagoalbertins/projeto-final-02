package com.example.projetolojaunit.controllers;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.projetolojaunit.entities.Marca;
import com.example.projetolojaunit.repositories.MarcaRepository;
import com.example.projetolojaunit.services.exceptions.ResourceNotFoundException;

@Controller
@RequestMapping(path="/marca")
public class MarcaController {

	@Autowired
	private MarcaRepository marcaRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewMarca (@RequestParam String nome, String descricao) {
		
		if (nome == null || nome.length()==0) {
			return "Favor informar um nome válido";
		}
		
		Marca m = new Marca();
		m.setNome(nome);
		m.setDescricao(descricao);
		marcaRepository.save(m);
		
		return "Marca salva!";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Marca> getAllMarcas(){
		return marcaRepository.findAll();
	}
	
	@GetMapping(path="/{id}")
	public @ResponseBody ResponseEntity<Marca> returnMarca(@PathVariable Integer id) {
		return ResponseEntity.ok().body(marcaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
	}
	
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteMarca(@PathVariable Integer id) {
		try{
			marcaRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException a){
			return "Marca não encontrada!";
		}
		return "Marca deletada com sucesso!";
	}
	
	@PutMapping(path="/update/{id}")
	public @ResponseBody String updateMarca(@PathVariable Integer id, @RequestParam String nome, String descricao) {
		try{
			Marca m = marcaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
			m.setNome(nome);
			m.setDescricao(descricao);
			marcaRepository.save(m);
			return "Marca atualizada!";
		} catch (EntityNotFoundException e) {
			return "Marca não encontrada!";
		} catch (Exception e) {
			return "Marca já existe!";
		}
	}
}
