package com.alan.api.infrastructure.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alan.api.domain.model.Produto;
import com.alan.api.repository.ProdutoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class ProdutosRepositoryImpl implements ProdutoRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Produto> listarTodos() {
		return manager.createQuery(" from Produto", Produto.class).getResultList();
	}

	@Override
	public Produto buscarPorId(Long id) {
		return manager.find(Produto.class, id);
	}

	@Override
	@Transactional
	public Produto adicionar(Produto produto) {
		return manager.merge(produto);
	}

	@Override
	@Transactional
	public void remover(Produto produto) {
		produto = buscarPorId(produto.getId());
		manager.remove(produto);

	}



}
