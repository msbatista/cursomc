package com.marcelo.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcelo.cursomc.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
