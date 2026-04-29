package br.com.fastservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.fastservice.model.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {

	boolean existsByEmail(String email);

	boolean existsByCpf(String cpf);

	Optional<Cliente> findByEmail(String email);

	Optional<Cliente> findByCpf(String cpf);

}
