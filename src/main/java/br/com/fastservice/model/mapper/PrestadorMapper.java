package br.com.fastservice.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.fastservice.model.Prestador;
import br.com.fastservice.model.dto.PrestadorRequestDTO;
import br.com.fastservice.model.dto.PrestadorResponseDTO;

@Mapper(componentModel = "spring")
public interface PrestadorMapper {

	PrestadorResponseDTO toResponseDTO(Prestador prestador);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "perfil", ignore = true)
	Prestador toEntity(PrestadorRequestDTO dto);
}
