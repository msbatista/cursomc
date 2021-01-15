package com.marcelo.cursomc.services;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.marcelo.cursomc.domain.Categoria;
import com.marcelo.cursomc.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.marcelo.cursomc.domain.Produto;
import com.marcelo.cursomc.repository.ProdutoRepository;
import com.marcelo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto findById(Integer id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id));
    }

    public Page<Produto> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);

        return produtoRepository.findDistinctByNomeIgnoreCaseContainingAndCategoriasIn(name, categorias, pageRequest);
    }
}
