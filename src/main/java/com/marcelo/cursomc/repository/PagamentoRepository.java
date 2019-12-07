package com.marcelo.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcelo.cursomc.domain.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{

}
