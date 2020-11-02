package com.example.projetolojaunit.controllers;

import java.sql.Date;

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

import com.example.projetolojaunit.entities.Cliente;
import com.example.projetolojaunit.repositories.ClienteRepository;
import com.example.projetolojaunit.services.exceptions.ResourceNotFoundException;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewCliente(@RequestParam String nome, @RequestParam String cpf,
			@RequestParam String email, Date dataNascimento, String sexo, String nomeSocial, String apelido,
			String telefone) {

		if (nome == null || nome.length() == 0) {
			return "Favor informar um nome válido";
		}

		if (cpf == null || cpf.length() < 11) {
			return "Favor informar um cpf válido";
		}

		if (email == null || !email.contains("@")) {
			return "Favor informar um email válido";
		}
		
		if (clienteRepository.existsByCpf(cpf)) {
			return "Já existe um cliente com CPF informado";
		}

		Cliente c = new Cliente();
		c.setNome(nome);
		c.setCpf(cpf);
		c.setEmail(email);
		c.setDataNascimento(dataNascimento);
		c.setSexo(sexo);
		c.setNomeSocial(nomeSocial);
		c.setApelido(apelido);
		c.setTelefone(telefone);
		clienteRepository.save(c);

		return "Cliente salvo!";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Cliente> getAllClientes() {
		return clienteRepository.findAll();
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<Cliente> returnCliente(@PathVariable Integer id) {

		return ResponseEntity.ok()
				.body(clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
	}

	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deleteCliente(@PathVariable Integer id) {
		try {
			clienteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException a) {
			return "Cliente não encontrado!";
		}
		return "Cliente deletado com sucesso!";
	}

	@PutMapping(path = "/update/{id}")
	public @ResponseBody String updateCliente(@PathVariable Integer id, @RequestParam String nome,
			@RequestParam String cpf, @RequestParam String email, Date dataNascimento, String sexo, String nomeSocial,
			String apelido, String telefone) {
		
		if (clienteRepository.existsByCpf(cpf)) {
			return "Já existe outro cliente com CPF informado";
		}
		
		if (!clienteRepository.existsById(id)) {
			return "Cliente não encontrado";
		}

		if (nome == null || nome.length() == 0) {
			return "Favor informar um nome válido";
		}

		if (cpf == null || cpf.length() < 11) {
			return "Favor informar um cpf válido";
		}

		if (email == null || !email.contains("@")) {
			return "Favor informar um email válido";
		}
		

		Cliente c = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		c.setNome(nome);
		c.setCpf(cpf);
		c.setEmail(email);
		c.setDataNascimento(dataNascimento);
		c.setSexo(sexo);
		c.setNomeSocial(nomeSocial);
		c.setApelido(apelido);
		c.setTelefone(telefone);
		clienteRepository.save(c);
		return "Cliente atualizado!";

	}
}
