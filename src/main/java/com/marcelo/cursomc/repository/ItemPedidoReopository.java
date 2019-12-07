package com.marcelo.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcelo.cursomc.domain.ItemPedido;

@Repository
public interface ItemPedidoReopository extends JpaRepository<ItemPedido, Integer> {

}
