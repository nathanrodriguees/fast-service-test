package br.com.fastservice.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.fastservice.model.dto.ClienteRequestDTO;
import br.com.fastservice.model.dto.ClienteResponseDTO;
import br.com.fastservice.model.dto.ClienteUpdateDTO;
import br.com.fastservice.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/clientes")
@Tag(name = "Clientes", description = "Gerenciamento de clientes.")
public class ClienteController {

	private final ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@Operation(summary = "Cadastrar cliente", description = "Cria um novo cliente no sistema.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso.", content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content),
			@ApiResponse(responseCode = "409", description = "E-mail ou CPF já cadastrado.", content = @Content) })
	@PostMapping
	public ResponseEntity<ClienteResponseDTO> cadastrar(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para cadastro do cliente.", required = true) @RequestBody @Valid ClienteRequestDTO dto) {
		ClienteResponseDTO cliente = clienteService.cadastrar(dto);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();

		return ResponseEntity.created(location).body(cliente);
	}

	@Operation(summary = "Listar clientes", description = "Retorna todos os clientes cadastrados.")
	@ApiResponse(responseCode = "200", description = "Clientes encontrados.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteResponseDTO.class))))
	@GetMapping
	public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
		return ResponseEntity.ok(clienteService.listarTodos());
	}

	@Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente específico.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente encontrado.", content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado.", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> buscarPorId(
			@Parameter(description = "ID do cliente", example = "662d8f1a9b7e2c001234abcd") @PathVariable String id) {
		return ResponseEntity.ok(clienteService.buscarPorId(id));
	}

	@Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente. O CPF não pode ser alterado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso.", content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado.", content = @Content),
			@ApiResponse(responseCode = "409", description = "E-mail já cadastrado para outro cliente.", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> atualizar(
			@Parameter(description = "ID do cliente", example = "662d8f1a9b7e2c001234abcd") @PathVariable String id,

			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para atualização do cliente. O CPF não deve ser enviado.", required = true, content = @Content(schema = @Schema(implementation = ClienteUpdateDTO.class))) @RequestBody @Valid ClienteUpdateDTO dto) {
		ClienteResponseDTO clienteAtualizado = clienteService.atualizar(id, dto);
		return ResponseEntity.ok(clienteAtualizado);
	}

	@Operation(summary = "Deletar cliente", description = "Remove um cliente pelo ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso.", content = @Content),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado.", content = @Content) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(
			@Parameter(description = "ID do cliente", example = "662d8f1a9b7e2c001234abcd") @PathVariable String id) {
		clienteService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}