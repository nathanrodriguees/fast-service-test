package br.com.fastservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fastservice.model.Cliente;
import br.com.fastservice.model.Prestador;
import br.com.fastservice.model.dto.LoginRequestDTO;
import br.com.fastservice.model.dto.LoginResponseDTO;
import br.com.fastservice.repository.ClienteRepository;
import br.com.fastservice.repository.PrestadorRepository;

@Service
public class AuthService {

	private final ClienteRepository clienteRepository;
	private final PrestadorRepository prestadorRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(ClienteRepository clienteRepository, PrestadorRepository prestadorRepository,
			PasswordEncoder passwordEncoder) {
		this.clienteRepository = clienteRepository;
		this.prestadorRepository = prestadorRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public LoginResponseDTO login(LoginRequestDTO dto) {
		Cliente cliente = clienteRepository.findByEmail(dto.getEmail()).orElse(null);

		if (cliente != null) {
			if (!passwordEncoder.matches(dto.getPassword(), cliente.getPassword())) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos.");
			}

			return new LoginResponseDTO(cliente.getId(), cliente.getName(), cliente.getEmail(), cliente.getPerfil());
		}

		Prestador prestador = prestadorRepository.findByEmail(dto.getEmail()).orElse(null);

		if (prestador != null) {
			if (!passwordEncoder.matches(dto.getPassword(), prestador.getPassword())) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos.");
			}

			return new LoginResponseDTO(prestador.getId(), prestador.getName(), prestador.getEmail(),
					prestador.getPerfil());
		}

		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos.");
	}
}
