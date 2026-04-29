package br.com.fastservice.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fastservice.model.Cliente;
import br.com.fastservice.model.EPerfil;
import br.com.fastservice.model.dto.ClienteRequestDTO;
import br.com.fastservice.model.dto.ClienteResponseDTO;
import br.com.fastservice.model.dto.ClienteUpdateDTO;
import br.com.fastservice.model.mapper.ClienteMapper;
import br.com.fastservice.repository.ClienteRepository;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	private final ClienteMapper clienteMapper;
	private final PasswordEncoder passwordEncoder;

	public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper,
			PasswordEncoder passwordEncoder) {
		this.clienteRepository = clienteRepository;
		this.clienteMapper = clienteMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public ClienteResponseDTO cadastrar(ClienteRequestDTO dto) {
		if (clienteRepository.existsByEmail(dto.getEmail())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado.");
		}

		if (clienteRepository.existsByCpf(dto.getCpf())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado.");
		}

		Cliente cliente = clienteMapper.toEntity(dto);

		cliente.setPassword(passwordEncoder.encode(dto.getPassword()));
		cliente.setPerfil(EPerfil.CLIENTE);

		Cliente clienteSalvo = clienteRepository.save(cliente);

		return clienteMapper.toResponseDTO(clienteSalvo);
	}

	public List<ClienteResponseDTO> listarTodos() {
		return clienteRepository.findAll().stream().map(clienteMapper::toResponseDTO).toList();
	}

	public ClienteResponseDTO buscarPorId(String id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

		return clienteMapper.toResponseDTO(cliente);
	}

	public ClienteResponseDTO atualizar(String id, ClienteUpdateDTO dto) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

		clienteRepository.findByEmail(dto.getEmail()).ifPresent(clienteComMesmoEmail -> {
			if (!clienteComMesmoEmail.getId().equals(id)) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado para outro cliente.");
			}
		});

		cliente.setName(dto.getName());
		cliente.setEmail(dto.getEmail());
		cliente.setPassword(passwordEncoder.encode(dto.getPassword()));

		Cliente clienteAtualizado = clienteRepository.save(cliente);

		return clienteMapper.toResponseDTO(clienteAtualizado);
	}

	public void deletar(String id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

		clienteRepository.delete(cliente);
	}
}