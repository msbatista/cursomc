package com.marcelo.cursomc.resources;

import com.marcelo.cursomc.domain.Categoria;
import com.marcelo.cursomc.dto.CategoriaDTO;
import com.marcelo.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> findById(@PathVariable Integer id) {
        Categoria categoria = categoriaService.findById(id);

        return ResponseEntity.ok().body(categoria);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaService.fromDTO(categoriaDTO);

        categoria.setId(null);
        categoria = categoriaService.insert(categoria);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoria.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(
            @Valid @RequestBody CategoriaDTO categoriaDTO,
            @PathVariable Integer id) {

        Categoria categoria = categoriaService.fromDTO(categoriaDTO);
        categoria.setId(id);
        findById(id);

        categoria = categoriaService.update(categoria);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        findById(id);
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> getAll() {
        List<Categoria> categorias = categoriaService.findAll();

        List<CategoriaDTO> categoriaDTOs = categorias.stream()
                .map(CategoriaDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(categoriaDTOs);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        Page<Categoria> categoriaPage = categoriaService.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> categoriaDTOS = categoriaPage.map(CategoriaDTO::new);

        return ResponseEntity.ok().body(categoriaDTOS);
    }
}
