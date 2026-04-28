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
@Schema(name = "PrestadorResponse", description = "Dados retornados de um prestador.")
public class PrestadorResponseDTO {

	@Schema(description = "ID do prestador", example = "662d8f1a9b7e2c001234abcd")
	private String id;

	@Schema(description = "Nome do prestador ou empresa", example = "Fast Elétrica Serviços")
	private String name;

	@Schema(description = "E-mail do prestador", example = "contato@fasteletrica.com")
	private String email;

	@Schema(description = "CNPJ do prestador", example = "11222333000181")
	private String cnpj;

	@Schema(description = "Telefone do prestador", example = "11999999999")
	private String phone;

	@Schema(description = "Especialidade do prestador", example = "Eletricista")
	private String specialty;

	@Schema(description = "Perfil do usuário", example = "PRESTADOR")
	private EPerfil perfil;
}
