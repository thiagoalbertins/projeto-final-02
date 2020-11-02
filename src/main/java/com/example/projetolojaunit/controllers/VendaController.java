package com.example.projetolojaunit.controllers;

import java.time.LocalTime;

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

import com.example.projetolojaunit.entities.Venda;
import com.example.projetolojaunit.repositories.ClienteRepository;
import com.example.projetolojaunit.repositories.FormaPagamentoRepository;
import com.example.projetolojaunit.repositories.VendaRepository;
import com.example.projetolojaunit.services.exceptions.ResourceNotFoundException;

@Controller
@RequestMapping(path = "/venda")
public class VendaController {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private FormaPagamentoRepository formapagamentoRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewVenda(@RequestParam Integer id_cliente, @RequestParam Integer id_forma_pagamento,
			Double valor_total) {

		if (!clienteRepository.existsById(id_cliente)) {
			return "Cliente não existe";
		}
		if (!formapagamentoRepository.existsById(id_forma_pagamento)) {
			return "Forma de pagamento não existe";
		}

		Venda v = new Venda();
		v.setDatahora(LocalTime.now());
		v.setCliente(clienteRepository.findById(id_cliente).get());
		v.setForma_pagamento(formapagamentoRepository.findById(id_forma_pagamento).get());
		v.setValor_total(valor_total);
		vendaRepository.save(v);

		return "Venda salva!";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Venda> getAllVendas() {
		return vendaRepository.findAll();
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<Venda> returnVenda(@PathVariable Integer id) {
		return ResponseEntity.ok()
				.body(vendaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
	}

	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deleteVenda(@PathVariable Integer id) {
		try {
			vendaRepository.deleteById(id);
		} catch (EmptyResultDataAccessException a) {
			return "Venda não encontrada!";
		}
		return "Venda deletada com sucesso!";
	}

	@PutMapping(path = "/update/{id}")
	public @ResponseBody String updateVenda(@PathVariable Integer id, @RequestParam Integer id_cliente,
			@RequestParam Integer id_forma_pagamento, Double valor_total) {

		if (!vendaRepository.existsById(id)) {
			return "Venda não existe";
		}
		if (!clienteRepository.existsById(id_cliente)) {
			return "Cliente não existe";
		}
		if (!formapagamentoRepository.existsById(id_forma_pagamento)) {
			return "Forma de pagamento não existe";
		}

		Venda v = vendaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		v.setDatahora(LocalTime.now());
		v.setCliente(clienteRepository.findById(id_cliente).get());
		v.setForma_pagamento(formapagamentoRepository.findById(id_forma_pagamento).get());
		v.setValor_total(valor_total);
		vendaRepository.save(v);
		return "Venda atualizada!";
	}
}
