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

import com.example.projetolojaunit.entities.FormaPagamento;
import com.example.projetolojaunit.repositories.FormaPagamentoRepository;
import com.example.projetolojaunit.services.exceptions.ResourceNotFoundException;

@Controller
@RequestMapping(path = "/forma_pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formapagamentoRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewFormaPagamento(@RequestParam String forma, String descricao,
			@RequestParam Boolean ativo) {

		if (forma == null || forma.length()==0) {
			return "Forma de pagamento deve ser especificada";
		}

		if (ativo == null) {
			return "Deve ser informado se a forma de pagamento está ativa";
		}

		FormaPagamento f = new FormaPagamento();
		f.setForma(forma);
		f.setDescricao(descricao);
		f.setAtivo(ativo);
		formapagamentoRepository.save(f);

		return "Forma de pagamento salva!";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<FormaPagamento> getAllFormaPagamentos() {
		return formapagamentoRepository.findAll();
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<FormaPagamento> returnFormaPagamento(@PathVariable Integer id) {
		return ResponseEntity.ok()
				.body(formapagamentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
	}

	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deleteFormaPagamento(@PathVariable Integer id) {
		try {
			formapagamentoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException a) {
			return "Forma de Pagamento não encontrada!";
		}
		return "Forma de Pagamento deletada com sucesso!";
	}

	@PutMapping(path = "/update/{id}")
	public @ResponseBody String updateFormaPagamento(@PathVariable Integer id, @RequestParam String forma,
			String descricao, @RequestParam Boolean ativo) {

		if (!formapagamentoRepository.existsById(id)) {
			return "Forma de pagamento não encontrada";
		}
		
		if (forma == null || forma.length()==0) {
			return "Forma de pagamento deve ser especificada";
		}

		if (ativo == null) {
			return "Deve ser informado se a forma de pagamento está ativa";
		}

		FormaPagamento f = formapagamentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		f.setForma(forma);
		f.setDescricao(descricao);
		f.setAtivo(ativo);
		formapagamentoRepository.save(f);
		return "Forma de Pagamento atualizada!";
	}
}
