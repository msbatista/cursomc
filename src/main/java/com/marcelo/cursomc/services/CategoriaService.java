package com.marcelo.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.marcelo.cursomc.domain.Categoria;
import com.marcelo.cursomc.dto.CategoriaDTO;
import com.marcelo.cursomc.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.marcelo.cursomc.repository.CategoriaRepository;
import com.marcelo.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria findById(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id));
	}
	
	public Categoria insert(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		Categoria categoriaToUpdate = findById(categoria.getId());

		updateData(categoria, categoriaToUpdate);

		return categoriaRepository.save(categoriaToUpdate);
	}

	public void deleteById(Integer id) {
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Can not delete object with nested items associated!");
		}
	}

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return  new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}

	private void updateData(Categoria categoria, Categoria categoriaToUpdate) {
		categoriaToUpdate.setNome(categoria.getNome());
	}
}
