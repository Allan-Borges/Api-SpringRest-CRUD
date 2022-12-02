package com.alan.api.domain.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.alan.api.domain.model.Produto;
import com.alan.api.domain.model.service.exceptions.EntidadeEmUsoExeception;
import com.alan.api.domain.model.service.exceptions.EntidadeNaoEncontradaException;
import com.alan.api.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Produto salvar(Produto produto) {
		return produtoRepository.adicionar(produto);
	}
	
	public void remover(Long produtoId) {
		Produto produto = produtoRepository.buscarPorId(produtoId);
		
		try {
			produtoRepository.remover(produto);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("O produto com o cadastro %d não foi encontrado", produtoId));
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoExeception(String.format("O produto com o cadastro %d está em uso", produtoId));
		}
		
	}
}
