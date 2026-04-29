package br.com.fastservice.model.dto;

import br.com.fastservice.model.EPerfil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "LoginResponse", description = "Dados retornados após login.")
public class LoginResponseDTO {

	@Schema(description = "ID do usuário", example = "662d8f1a9b7e2c001234abcd")
	private String id;

	@Schema(description = "Nome do usuário", example = "Nathan Rodrigues")
	private String name;

	@Schema(description = "E-mail do usuário", example = "nathan@email.com")
	private String email;

	@Schema(description = "Perfil do usuário", example = "CLIENTE")
	private EPerfil perfil;
}
