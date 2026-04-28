package br.com.fastservice.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fastservice.model.EPerfil;
import br.com.fastservice.model.Prestador;
import br.com.fastservice.model.dto.PrestadorRequestDTO;
import br.com.fastservice.model.dto.PrestadorResponseDTO;
import br.com.fastservice.model.dto.PrestadorUpdateDTO;
import br.com.fastservice.model.mapper.PrestadorMapper;
import br.com.fastservice.repository.PrestadorRepository;

@Service
public class PrestadorService {

	private final PrestadorRepository prestadorRepository;
	private final PrestadorMapper prestadorMapper;
	private final PasswordEncoder passwordEncoder;

	public PrestadorService(PrestadorRepository prestadorRepository, PrestadorMapper prestadorMapper,
			PasswordEncoder passwordEncoder) {
		this.prestadorRepository = prestadorRepository;
		this.prestadorMapper = prestadorMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public PrestadorResponseDTO cadastrar(PrestadorRequestDTO dto) {
		if (prestadorRepository.existsByEmail(dto.getEmail())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado.");
		}

		if (prestadorRepository.existsByCnpj(dto.getCnpj())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "CNPJ já cadastrado.");
		}

		Prestador prestador = prestadorMapper.toEntity(dto);

		prestador.setPassword(passwordEncoder.encode(dto.getPassword()));
		prestador.setPerfil(EPerfil.PRESTADOR);

		Prestador prestadorSalvo = prestadorRepository.save(prestador);

		return prestadorMapper.toResponseDTO(prestadorSalvo);
	}

	public List<PrestadorResponseDTO> listarTodos() {
		return prestadorRepository.findAll().stream().map(prestadorMapper::toResponseDTO).toList();
	}

	public PrestadorResponseDTO buscarPorId(String id) {
		Prestador prestador = prestadorRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestador não encontrado."));

		return prestadorMapper.toResponseDTO(prestador);
	}

	public PrestadorResponseDTO atualizar(String id, PrestadorUpdateDTO dto) {
		Prestador prestador = prestadorRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestador não encontrado."));

		prestadorRepository.findByEmail(dto.getEmail()).ifPresent(prestadorComMesmoEmail -> {
			if (!prestadorComMesmoEmail.getId().equals(id)) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado para outro prestador.");
			}
		});

		prestador.setName(dto.getName());
		prestador.setEmail(dto.getEmail());
		prestador.setPassword(passwordEncoder.encode(dto.getPassword()));
		prestador.setPhone(dto.getPhone());
		prestador.setSpecialty(dto.getSpecialty());

		Prestador prestadorAtualizado = prestadorRepository.save(prestador);

		return prestadorMapper.toResponseDTO(prestadorAtualizado);
	}

	public void deletar(String id) {
		Prestador prestador = prestadorRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestador não encontrado."));

		prestadorRepository.delete(prestador);
	}
}
