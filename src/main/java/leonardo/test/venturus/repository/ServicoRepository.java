package leonardo.test.venturus.repository;

import leonardo.test.venturus.domain.Servico;
import leonardo.test.venturus.service.dto.ServicoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

	@Query("SELECT new leonardo.test.venturus.service.dto.ServicoDTO(s.id, s.nome, s.descricao) FROM Servico s "
		+ " WHERE :nome IS NULL OR LOWER(s.nome) LIKE LOWER(CONCAT(CONCAT('%', :nome), '%'))")
	Page<ServicoDTO> findByNome(@Param("nome") String nome, Pageable pageable);

	@Query("SELECT CASE WHEN count(s.id) > 0 THEN TRUE ELSE FALSE END"
		+ " FROM Servico s WHERE LOWER(s.nome) = LOWER(:nome) AND (:id IS NULL OR s.id != :id)")
	Boolean existsByNome(@Param("nome") String nome, @Param("id") Long id);
}
