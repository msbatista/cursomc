package com.marcelo.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcelo.cursomc.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
