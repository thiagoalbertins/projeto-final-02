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

import com.example.projetolojaunit.entities.Fornecedor;
import com.example.projetolojaunit.repositories.FornecedorRepository;
import com.example.projetolojaunit.services.exceptions.ResourceNotFoundException;

@Controller
@RequestMapping(path = "/fornecedor")
public class FornecedorController {

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewFornecedor(@RequestParam String nome, String endereco, String telefone,
			@RequestParam String cnpj, String email) {

		if (nome == null || nome.length() == 0) {
			return "Favor digitar um nome válido";
		}

		if (cnpj == null || cnpj.length() < 14) {
			return "Favor digitar CNPJ válido";
		}
		
		if (fornecedorRepository.existsByCnpj(cnpj)) {
			return "Já existe outro cliente com CPF informado";
		}

		Fornecedor f = new Fornecedor();
		f.setNome(nome);
		f.setEndereco(endereco);
		f.setTelefone(telefone);
		f.setCnpj(cnpj);
		f.setEmail(email);
		fornecedorRepository.save(f);

		return "Fornecedor salvo!";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Fornecedor> getAllFornecedor() {
		return fornecedorRepository.findAll();
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<Fornecedor> returnFornecedor(@PathVariable Integer id) {
		return ResponseEntity.ok()
				.body(fornecedorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
	}

	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deleteFornecedor(@PathVariable Integer id) {
		try {
			fornecedorRepository.deleteById(id);
		} catch (EmptyResultDataAccessException a) {
			return "Forneceor não encontrado!";
		}
		return "Fornecedor deletado com sucesso!";
	}

	@PutMapping(path = "/update/{id}")
	public @ResponseBody String updateFornecedor(@PathVariable Integer id, @RequestParam String nome, String endereco,
			String telefone, @RequestParam String cnpj, String email) {

		if (fornecedorRepository.existsByCnpj(cnpj)) {
			return "Já existe outro cliente com CPF informado";
		}
		
		if (!fornecedorRepository.existsById(id)) {
			return "Fornecedor não encontrado";
		}
		
		if (nome == null || nome.length() == 0) {
			return "Favor digitar um nome válido";
		}

		if (cnpj == null || cnpj.length() < 11) {
			return "Favor digitar CNPJ válido";
		}

		Fornecedor f = fornecedorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		f.setNome(nome);
		f.setEndereco(endereco);
		f.setTelefone(telefone);
		f.setCnpj(cnpj);
		f.setEmail(email);
		fornecedorRepository.save(f);
		return "Fornecedor atualizado!";

	}
}
