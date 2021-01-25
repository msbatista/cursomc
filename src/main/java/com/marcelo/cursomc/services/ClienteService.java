package com.marcelo.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.marcelo.cursomc.domain.Cidade;
import com.marcelo.cursomc.domain.Cliente;
import com.marcelo.cursomc.domain.Endereco;
import com.marcelo.cursomc.dto.ClienteDTO;
import com.marcelo.cursomc.dto.ClienteNewDTO;
import com.marcelo.cursomc.domain.enums.TipoCliente;
import com.marcelo.cursomc.repository.EnderecoRepository;
import com.marcelo.cursomc.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.marcelo.cursomc.repository.ClienteRepository;
import com.marcelo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);

		enderecoRepository.saveAll(cliente.getEnderecos());

		return clienteRepository.save(cliente);
	}
	
	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Client not found! Id: " + id));
	}

	public Cliente update(Cliente cliente) {
		Cliente clienteToUpdate = findById(cliente.getId());

		updatedClienteObj(cliente, clienteToUpdate);

		return clienteRepository.save(clienteToUpdate);
	}

	public void deleteById(Integer id) {
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Can not delete client object with orders associated!");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cliente cliente = new Cliente(
				null,
				clienteNewDTO.getNome(),
				clienteNewDTO.getEmail(),
				clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipo())
		);

		Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);

		Endereco endereco =  new Endereco(
				null,
				clienteNewDTO.getLogradouro(),
				clienteNewDTO.getNumero(),
				clienteNewDTO.getComplemento(),
				clienteNewDTO.getBairro(),
				clienteNewDTO.getCep(),
				cliente,
				cidade
		);

		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteNewDTO.getTelefone1());

		if (clienteNewDTO.getTelefone2() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		}

		if (clienteNewDTO.getTelefone3() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		}

		return cliente;
	}

	private void updatedClienteObj(Cliente cliente, Cliente clienteToUpdate) {
		clienteToUpdate.setNome(cliente.getNome());
		clienteToUpdate.setEmail(cliente.getEmail());
	}
}
