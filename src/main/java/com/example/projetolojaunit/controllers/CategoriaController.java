package com.example.projetolojaunit.controllers;

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

import com.example.projetolojaunit.entities.Categoria;
import com.example.projetolojaunit.repositories.CategoriaRepository;
import com.example.projetolojaunit.services.exceptions.ResourceNotFoundException;

@Controller
@RequestMapping(path = "/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewCategoria(String nome, @RequestParam Boolean ativo) {

		if (ativo == null) {
			return "Deve ser informado se a categoria está ativa";
		}

		Categoria c = new Categoria();
		c.setNome(nome);
		c.setAtivo(ativo);
		categoriaRepository.save(c);

		return "Categoria salva!";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Categoria> getAllCategorias() {
		return categoriaRepository.findAll();
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<Categoria> returnCategoria(@PathVariable Integer id) {
		return ResponseEntity.ok()
				.body(categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
	}

	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deleteCategoria(@PathVariable Integer id) {
		try {
			categoriaRepository.deleteById(id);
			return "Categoria deletada com sucesso!";
		} catch (EmptyResultDataAccessException a) {
			return "Categoria não encontrada!";
		}
	}

	@PutMapping(path = "/update/{id}")
	public @ResponseBody String updateCategoria(@PathVariable Integer id, String nome, @RequestParam Boolean ativo) {

		if (!categoriaRepository.existsById(id)) {
			return "Categoria não encontrada";
		}

		if (ativo == null) {
			return "Deve ser informado se a categoria está ativa";
		}

		Categoria c = categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		c.setNome(nome);
		c.setAtivo(ativo);
		categoriaRepository.save(c);
		return "Categoria atualizada!";

	}
}
