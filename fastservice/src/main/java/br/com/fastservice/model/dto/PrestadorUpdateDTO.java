package br.com.fastservice.model.dto;

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
@Schema(name = "PrestadorUpdate", description = "Dados para atualização de prestador. O CNPJ não pode ser alterado.")
public class PrestadorUpdateDTO {

	@NotBlank
	@Size(min = 2, max = 120)
	@Schema(description = "Nome do prestador ou empresa", example = "Fast Elétrica Serviços")
	private String name;

	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "E-mail inválido")
	@Schema(description = "E-mail do prestador", example = "contato@fasteletrica.com")
	private String email;

	@NotBlank(message = "A senha é obrigatória")
	@Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[\\p{Punct}])\\S{8,}$", message = "A senha deve conter pelo menos 1 letra maiúscula, 1 número e 1 símbolo")
	@Schema(description = "Senha do prestador. Deve conter no mínimo 8 caracteres, 1 letra maiúscula, 1 número e 1 símbolo.", example = "Senha@123", format = "password", accessMode = Schema.AccessMode.WRITE_ONLY)
	private String password;

	@NotBlank(message = "O telefone é obrigatório")
	@Schema(description = "Telefone do prestador", example = "11999999999")
	private String phone;

	@NotBlank(message = "A especialidade é obrigatória")
	@Schema(description = "Especialidade do prestador", example = "Eletricista")
	private String specialty;
}
