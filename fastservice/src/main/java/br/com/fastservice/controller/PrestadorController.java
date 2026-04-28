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

import br.com.fastservice.model.dto.PrestadorRequestDTO;
import br.com.fastservice.model.dto.PrestadorResponseDTO;
import br.com.fastservice.model.dto.PrestadorUpdateDTO;
import br.com.fastservice.service.PrestadorService;
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
@RequestMapping("api/prestadores")
@Tag(name = "Prestadores", description = "Gerenciamento de prestadores de serviço.")
public class PrestadorController {

	private final PrestadorService prestadorService;

	public PrestadorController(PrestadorService prestadorService) {
		this.prestadorService = prestadorService;
	}

	@Operation(summary = "Cadastrar prestador", description = "Cria um novo prestador no sistema.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Prestador cadastrado com sucesso.", content = @Content(schema = @Schema(implementation = PrestadorResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content),
			@ApiResponse(responseCode = "409", description = "E-mail ou CNPJ já cadastrado.", content = @Content) })
	@PostMapping
	public ResponseEntity<PrestadorResponseDTO> cadastrar(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para cadastro do prestador.", required = true) @RequestBody @Valid PrestadorRequestDTO dto) {
		PrestadorResponseDTO prestador = prestadorService.cadastrar(dto);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(prestador.getId())
				.toUri();

		return ResponseEntity.created(location).body(prestador);
	}

	@Operation(summary = "Listar prestadores", description = "Retorna todos os prestadores cadastrados.")
	@ApiResponse(responseCode = "200", description = "Prestadores encontrados.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PrestadorResponseDTO.class))))
	@GetMapping
	public ResponseEntity<List<PrestadorResponseDTO>> listarTodos() {
		return ResponseEntity.ok(prestadorService.listarTodos());
	}

	@Operation(summary = "Buscar prestador por ID", description = "Retorna os dados de um prestador específico.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Prestador encontrado.", content = @Content(schema = @Schema(implementation = PrestadorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Prestador não encontrado.", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<PrestadorResponseDTO> buscarPorId(
			@Parameter(description = "ID do prestador", example = "662d8f1a9b7e2c001234abcd") @PathVariable String id) {
		return ResponseEntity.ok(prestadorService.buscarPorId(id));
	}

	@Operation(summary = "Atualizar prestador", description = "Atualiza os dados de um prestador existente. O CNPJ não pode ser alterado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Prestador atualizado com sucesso.", content = @Content(schema = @Schema(implementation = PrestadorResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content),
			@ApiResponse(responseCode = "404", description = "Prestador não encontrado.", content = @Content),
			@ApiResponse(responseCode = "409", description = "E-mail já cadastrado para outro prestador.", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<PrestadorResponseDTO> atualizar(
			@Parameter(description = "ID do prestador", example = "662d8f1a9b7e2c001234abcd") @PathVariable String id,

			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para atualização do prestador. O CNPJ não deve ser enviado.", required = true, content = @Content(schema = @Schema(implementation = PrestadorUpdateDTO.class))) @RequestBody @Valid PrestadorUpdateDTO dto) {
		PrestadorResponseDTO prestadorAtualizado = prestadorService.atualizar(id, dto);
		return ResponseEntity.ok(prestadorAtualizado);
	}

	@Operation(summary = "Deletar prestador", description = "Remove um prestador pelo ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Prestador deletado com sucesso.", content = @Content),
			@ApiResponse(responseCode = "404", description = "Prestador não encontrado.", content = @Content) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(
			@Parameter(description = "ID do prestador", example = "662d8f1a9b7e2c001234abcd") @PathVariable String id) {
		prestadorService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
