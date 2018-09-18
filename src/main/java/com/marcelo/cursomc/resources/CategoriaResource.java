package com.marcelo.cursomc.resources;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> listar() {
		Categoria cat1 = new Categoria(1, "Infomatica");
		Categoria cat2 = new Categoria(2, "Escritorio");
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		categorias.add(cat1);
		categorias.add(cat2);
		
		return categorias;
	}
}
