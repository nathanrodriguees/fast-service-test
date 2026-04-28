package br.com.fastservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "LoginRequest", description = "Dados para autenticação.")
public class LoginRequestDTO {

	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "E-mail inválido")
	@Schema(description = "E-mail do usuário", example = "nathan@email.com")
	private String email;

	@NotBlank(message = "A senha é obrigatória")
	@Schema(description = "Senha do usuário", example = "123456", format = "password")
	private String password;
}
