package com.marcelo.cursomc.resources;

import com.marcelo.cursomc.domain.Produto;
import com.marcelo.cursomc.domain.dto.ProdutoDTO;
import com.marcelo.cursomc.resources.utils.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marcelo.cursomc.services.ProdutoService;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
    @Autowired
    private ProdutoService produtoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id) {
        return ResponseEntity.ok(produtoService.findById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        List<Integer> ids = Url.decodeIntList(categorias);
        String nomeDecoded = Url.decodeParam(nome);

        Page<Produto> produtoPage = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> produtoDTOS = produtoPage.map(ProdutoDTO::new);

        return ResponseEntity.ok().body(produtoDTOS);
    }
}
