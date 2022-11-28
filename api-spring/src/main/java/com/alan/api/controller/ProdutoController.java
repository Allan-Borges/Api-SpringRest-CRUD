package com.alan.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alan.api.domain.model.Produto;
import com.alan.api.domain.model.service.CadastroProdutoService;
import com.alan.api.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@GetMapping
	public List<Produto> listar() {
		return produtoRepository.listarTodos();
	}

	@GetMapping("/{produtoId}")
	public ResponseEntity<Produto> buscar(@PathVariable Long produtoId) {
		Produto produto = produtoRepository.buscarPorId(produtoId);

		if (produto != null) {
			return ResponseEntity.ok(produto);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto adicionar(@RequestBody Produto produto) {
		return cadastroProdutoService.salvar(produto);
	}

	@PutMapping("/{produtoId}")
	public ResponseEntity<Produto> atualizar(@PathVariable Long produtoId, @RequestBody Produto produto) {
		Produto produtoAtual = produtoRepository.buscarPorId(produtoId);

		if (produtoAtual != null) {
			BeanUtils.copyProperties(produto, produtoAtual, "id");
			produtoRepository.adicionar(produtoAtual);
			return ResponseEntity.ok(produtoAtual);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{produtoId}")
	public ResponseEntity<Produto> remover(@PathVariable Long produtoId) {

		Produto produto = produtoRepository.buscarPorId(produtoId);

		try {
			if (produto != null) {
				produtoRepository.remover(produto);
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.notFound().build();
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
