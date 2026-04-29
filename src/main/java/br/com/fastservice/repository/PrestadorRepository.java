package br.com.fastservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.fastservice.model.Prestador;

@Repository
public interface PrestadorRepository extends MongoRepository<Prestador, String> {

	boolean existsByEmail(String email);

	boolean existsByCnpj(String cnpj);

	Optional<Prestador> findByEmail(String email);

	Optional<Prestador> findByCnpj(String cnpj);
}
