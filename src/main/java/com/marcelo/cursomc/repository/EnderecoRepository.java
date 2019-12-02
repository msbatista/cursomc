package com.marcelo.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcelo.cursomc.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
