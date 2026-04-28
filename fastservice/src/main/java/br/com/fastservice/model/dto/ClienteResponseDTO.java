package br.com.fastservice.model.dto;

import br.com.fastservice.model.EPerfil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "ClienteResponse", description = "Dados retornados de um cliente.")
public class ClienteResponseDTO {

	@Schema(description = "ID do cliente", example = "662d8f1a9b7e2c001234abcd")
	private String id;

	@Schema(description = "Nome completo do cliente", example = "Nathan Rodrigues")
	private String name;

	@Schema(description = "E-mail do cliente", example = "nathan@email.com")
	private String email;

	@Schema(description = "CPF do cliente", example = "52998224725")
	private String cpf;

	@Schema(description = "Perfil do usuário", example = "CLIENTE")
	private EPerfil perfil;
}
