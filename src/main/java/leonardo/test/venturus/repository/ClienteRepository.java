package leonardo.test.venturus.repository;

import leonardo.test.venturus.domain.Cliente;
import leonardo.test.venturus.service.dto.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("SELECT new leonardo.test.venturus.service.dto.ClienteDTO(c.id, c.cnpj, c.nome) FROM Cliente c "
		+ " WHERE :nome IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT(CONCAT('%', :nome), '%'))")
	Page<ClienteDTO> findByNome(@Param("nome") String nome, Pageable pageable);

	@Query("SELECT CASE WHEN count(c.id) > 0 THEN TRUE ELSE FALSE END"
		+ " FROM Cliente c WHERE c.cnpj = :cnpj AND (:id IS NULL OR c.id != :id)")
	Boolean existsByCnpj(@Param("cnpj") String cnpj, @Param("id") Long id);
}
