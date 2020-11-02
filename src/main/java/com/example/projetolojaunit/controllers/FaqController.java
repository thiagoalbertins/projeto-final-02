package com.example.projetolojaunit.controllers;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projetolojaunit.entities.Faq;
import com.example.projetolojaunit.repositories.FaqRepository;
import com.example.projetolojaunit.repositories.ProdutoRepository;
import com.example.projetolojaunit.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(path = "/faq")
public class FaqController {

	@Autowired
	private FaqRepository faqRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewFaq(@RequestParam String texto, @RequestParam Integer id_produto) {
		
		if (id_produto == null) {
			return "Favor informar id de produto válido";
		}
		if (!produtoRepository.existsById(id_produto)) {
			return "Produto não existe";
		}
		
		Faq f = new Faq();
		f.setDatahora(LocalTime.now());
		f.setTexto(texto);
		f.setProduto(produtoRepository.findById(id_produto).get());
		faqRepository.save(f);
		return "Faq salva!";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Faq> getAllFaqs() {
		return faqRepository.findAll();
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<Faq> returnFaq(@PathVariable Integer id) {

		return ResponseEntity.ok()
				.body(faqRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
	}

	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deleteFaq(@PathVariable Integer id) {
		try {
			faqRepository.deleteById(id);
		} catch (EmptyResultDataAccessException a) {
			return "Faq não encontrada!";
		}
		return "Faq deletada com sucesso!";
	}

	@PutMapping(path = "/update/{id}")
	public @ResponseBody String updateFaq(@PathVariable Integer id, LocalTime datahora, @RequestParam String texto,
			@RequestParam Integer id_produto) {

		if (!faqRepository.existsById(id)) {
			return "Faq não existe";
		}
		if (!produtoRepository.existsById(id_produto)) {
			return "Produto não existe";
		}

		Faq f = faqRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		f.setDatahora(LocalTime.now());
		f.setTexto(texto);
		f.setProduto(produtoRepository.findById(id_produto).get());
		faqRepository.save(f);
		return "Faq atualizada!";

	}
}
