package br.com.fastservice.model;

import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "prestadores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Prestador {

	@Id
	private String id;

	@NotBlank
	@Size(min = 2, max = 120)
	private String name;

	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "E-mail inválido")
	private String email;

	@NotBlank
	private String password;

	@CNPJ(message = "CNPJ inválido")
	@NotBlank(message = "O CNPJ é obrigatório")
	private String cnpj;

	@NotBlank(message = "O telefone é obrigatório")
	private String phone;

	@NotBlank(message = "A especialidade é obrigatória")
	private String specialty;

	private EPerfil perfil;

}
