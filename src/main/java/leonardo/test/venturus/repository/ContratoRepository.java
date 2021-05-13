package leonardo.test.venturus.repository;

import leonardo.test.venturus.domain.Contrato;
import leonardo.test.venturus.service.dto.ContratoDTO;
import leonardo.test.venturus.service.dto.ContratoListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

	@Query("SELECT new leonardo.test.venturus.service.dto.ContratoListDTO(c.id, c.vigencia, c.cliente.nome, c.servico.nome) FROM Contrato c "
		+ " WHERE :cnpj IS NULL OR :cnpj = c.cliente.cnpj")
	Page<ContratoListDTO> findByCnpj(@Param("cnpj") String cnpj, Pageable pageable);

	@Query("SELECT CASE WHEN count(c.id) > 0 THEN TRUE ELSE FALSE END "
		+ " FROM Contrato c WHERE c.cliente.id = :#{#contrato.idCliente} AND c.servico.id = :#{#contrato.idServico} "
		+ "AND (:#{#contrato.id} IS NULL OR c.id != :#{#contrato.id})")
	Boolean existsByClienteIdAndServicoId(@Param("contrato") ContratoDTO contratoDTO);
}
