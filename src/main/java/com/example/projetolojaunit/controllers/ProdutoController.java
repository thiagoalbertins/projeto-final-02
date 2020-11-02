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

import com.example.projetolojaunit.entities.Produto;
import com.example.projetolojaunit.repositories.CategoriaRepository;
import com.example.projetolojaunit.repositories.FornecedorRepository;
import com.example.projetolojaunit.repositories.MarcaRepository;
import com.example.projetolojaunit.repositories.ProdutoRepository;
import com.example.projetolojaunit.services.exceptions.ResourceNotFoundException;

@Controller
@RequestMapping(path = "/produto")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private MarcaRepository marcaRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewProduto(@RequestParam String nome, String descricao, Double preco_unitario,
			String unidade, @RequestParam Integer id_categoria, @RequestParam Integer id_fornecedor, @RequestParam Integer id_marca) {

		if(nome == null || nome.length()==0) {
			return "Favor informar um nome válido";
		}
		
		if(id_categoria == null) {
			return "Favor informar uma categoria válida";
		}

		if(id_fornecedor == null) {
			return "Favor informar um fornecedor válido";
		}
		
		if(id_marca == null) {
			return "Favor informar uma marca válida";
		}
		
		
		if (!categoriaRepository.existsById(id_categoria)) {
			return "Categoria não existe";
		}

		if (!marcaRepository.existsById(id_marca)) {
			return "Marca não existe";
		}

		if (!fornecedorRepository.existsById(id_fornecedor)) {
			return "Fornecedor não existe";
		}

		Produto p = new Produto();
		p.setNome(nome);
		p.setDescricao(descricao);
		p.setPreco_unitario(preco_unitario);
		p.setUnidade(unidade);
		p.setCategoria(categoriaRepository.findById(id_categoria).get());
		p.setFornecedor(fornecedorRepository.findById(id_fornecedor).get());
		p.setMarca(marcaRepository.findById(id_marca).get());
		produtoRepository.save(p);

		return "Produto salvo!";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Produto> getAllProdutos() {
		return produtoRepository.findAll();
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<Produto> returnProduto(@PathVariable Integer id) {
		return ResponseEntity.ok()
				.body(produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
	}

	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deleteProduto(@PathVariable Integer id) {
		try {
			produtoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException a) {
			return "Produto não encontrado!";
		}
		return "Produto deletado com sucesso!";
	}

	@PutMapping(path = "/update/{id}")
	public @ResponseBody String updateProduto(@PathVariable Integer id, @RequestParam String nome, String descricao,
			Double preco_unitario, String unidade, Integer id_categoria, Integer id_fornecedor, Integer id_marca) {

		if(nome == null || nome.length()==0) {
			return "Favor informar um nome válido";
		}
		
		if(id_categoria == null) {
			return "Favor informar uma categoria válida";
		}

		if(id_fornecedor == null) {
			return "Favor informar um fornecedor válido";
		}
		
		if(id_marca == null) {
			return "Favor informar uma marca válida";
		}
		
		if (!categoriaRepository.existsById(id_categoria)) {
			return "Categoria não existe";
		}

		if (!marcaRepository.existsById(id_marca)) {
			return "Marca não existe";
		}

		if (!fornecedorRepository.existsById(id_fornecedor)) {
			return "Fornecedor não existe";
		}

		if (!produtoRepository.existsById(id)) {
			return "Produto não existe";
		}

		Produto p = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		p.setNome(nome);
		p.setDescricao(descricao);
		p.setPreco_unitario(preco_unitario);
		p.setUnidade(unidade);
		p.setCategoria(categoriaRepository.findById(id_categoria).get());
		p.setFornecedor(fornecedorRepository.findById(id_fornecedor).get());
		p.setMarca(marcaRepository.findById(id_marca).get());
		produtoRepository.save(p);
		return "Produto atualizado!";

	}
}
