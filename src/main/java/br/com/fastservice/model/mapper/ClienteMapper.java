package br.com.fastservice.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.fastservice.model.Cliente;
import br.com.fastservice.model.dto.ClienteRequestDTO;
import br.com.fastservice.model.dto.ClienteResponseDTO;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

	ClienteResponseDTO toResponseDTO(Cliente cliente);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "perfil", ignore = true)
	Cliente toEntity(ClienteRequestDTO dto);
}
