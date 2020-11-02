package com.example.projetolojaunit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.projetolojaunit.entities.ItensVenda;
import com.example.projetolojaunit.entities.ItensVendaKey;
import com.example.projetolojaunit.repositories.ItensVendaRepository;
import com.example.projetolojaunit.repositories.ProdutoRepository;
import com.example.projetolojaunit.repositories.VendaRepository;
import com.example.projetolojaunit.services.exceptions.ResourceNotFoundException;

@Controller
@RequestMapping(path = "/itens_venda")
public class ItensVendaController {

	@Autowired
	private ItensVendaRepository itensvendaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private VendaRepository vendaRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewItensVenda(@RequestParam int id_venda, @RequestParam int id_produto,
			@RequestParam Integer quantidade, @RequestParam Double valor_unitario) {

		if (!vendaRepository.existsById(id_venda)) {
			return "Venda com id: " + id_venda + " não encontrada";
		}

		if (!produtoRepository.existsById(id_produto)) {
			return "Produto com id: " + id_produto + " não encontrado";
		}

		if (quantidade == null || quantidade == 0) {
			return "Favor informar quantidade válida";
		}

		if (valor_unitario == null || valor_unitario == 0) {
			return "Favor informar um valor unitário válido";
		}

		ItensVenda i = new ItensVenda();
		ItensVendaKey ik = new ItensVendaKey();
		ik.setId_venda(id_venda);
		ik.setId_produto(id_produto);
		i.setItens_venda_key(ik);
		i.setQuantidade(quantidade);
		i.setValor_unitario(valor_unitario);
		itensvendaRepository.save(i);

		return "Itens da Venda salvo!";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<ItensVenda> getAllItensVenda() {
		return itensvendaRepository.findAll();
	}

	@GetMapping(path = "/{id_venda}/{id_produto}")
	public @ResponseBody List<ItensVenda> returnItensVenda(@PathVariable Integer id_venda,
			@PathVariable Integer id_produto) {

		return itensvendaRepository.findByItensVendaKey(new ItensVendaKey(id_venda, id_produto));
	}

	@DeleteMapping(path = "/delete/{id_venda}/{id_produto}")
	public @ResponseBody String deleteItensVenda(@PathVariable Integer id_venda, @PathVariable Integer id_produto) {

		if (!itensvendaRepository.existsByItensVendaKey(new ItensVendaKey(id_venda, id_produto))) {
			return "Itens de venda não encontrados";
		}
		
		itensvendaRepository.deleteByItensVendaKey(new ItensVendaKey(id_venda, id_produto));

		return "Itens da Venda deletado com sucesso!";
	}

	@PutMapping(path = "/update/{id_venda}/{id_produto}")
	public @ResponseBody String updateItensVenda(@PathVariable Integer id_venda, @PathVariable Integer id_produto,
			@RequestParam Integer quantidade, @RequestParam Double valor_unitario) {

		if (!itensvendaRepository.existsByItensVendaKey(new ItensVendaKey(id_venda, id_produto))) {
			return "Itens de venda não encontrados";
		}

		if (!vendaRepository.existsById(id_venda)) {
			return "Venda com id: " + id_venda + " não encontrada";
		}

		if (!produtoRepository.existsById(id_produto)) {
			return "Produto com id: " + id_produto + " não encontrado";
		}
		
		if (quantidade == null || quantidade == 0) {
			return "Favor informar quantidade válida";
		}

		if (valor_unitario == null || valor_unitario == 0) {
			return "Favor informar um valor unitário válido";
		}

		ItensVenda i = itensvendaRepository.findById(id_venda)
				.orElseThrow(() -> new ResourceNotFoundException(id_venda));
		ItensVendaKey ik = new ItensVendaKey();
		ik.setId_venda(id_venda);
		ik.setId_produto(id_produto);
		i.setItens_venda_key(ik);
		i.setQuantidade(quantidade);
		i.setValor_unitario(valor_unitario);
		itensvendaRepository.save(i);
		return "Itens da Venda atualizado!";

	}

}
