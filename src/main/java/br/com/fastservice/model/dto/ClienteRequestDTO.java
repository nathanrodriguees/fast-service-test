package br.com.fastservice.model.dto;

import org.hibernate.validator.constraints.br.CPF;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "ClienteRequest", description = "Dados para cadastro ou atualização de cliente.")
public class ClienteRequestDTO {

	@NotBlank
	@Size(min = 2, max = 120)
	@Schema(description = "Nome completo do cliente", example = "Nathan Rodrigues")
	private String name;

	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "E-mail inválido")
	@Schema(description = "E-mail do cliente", example = "nathan@email.com")
	private String email;

	@NotBlank(message = "A senha é obrigatória")
	@Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[\\p{Punct}])\\S{8,}$", message = "A senha deve conter pelo menos 1 letra maiúscula, 1 número e 1 símbolo")
	@Schema(description = "Senha do cliente. Deve conter no mínimo 8 caracteres, 1 letra maiúscula, 1 número e 1 símbolo.", example = "Senha@123", format = "password", accessMode = Schema.AccessMode.WRITE_ONLY)
	private String password;

	@CPF
	@NotBlank(message = "O CPF é obrigatório")
	@Schema(description = "CPF do cliente", example = "52998224725")
	private String cpf;
}
