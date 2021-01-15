package com.marcelo.cursomc.repository;

import com.marcelo.cursomc.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcelo.cursomc.domain.Produto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    @Transactional(readOnly = true)
    Page<Produto> findDistincByNomeIgnoreCaseContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
