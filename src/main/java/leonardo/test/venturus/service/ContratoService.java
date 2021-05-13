package leonardo.test.venturus.service;

import leonardo.test.venturus.domain.Contrato;
import leonardo.test.venturus.repository.ContratoRepository;
import leonardo.test.venturus.service.dto.ContratoDTO;
import leonardo.test.venturus.service.dto.ContratoListDTO;
import leonardo.test.venturus.service.mapper.ContratoMapper;
import leonardo.test.venturus.service.util.exception.DuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContratoService {

	private final ContratoRepository contratoRepository;

	private final ContratoMapper contratoMapper;

	public ContratoDTO save(ContratoDTO contratoDTO) {
		verifyExistence(contratoDTO);
		Contrato contrato = contratoMapper.toEntity(contratoDTO);
		return contratoMapper.toDto(contratoRepository.save(contrato));
	}

	private void verifyExistence(ContratoDTO contratoDTO) {
		if (contratoRepository.existsByClienteIdAndServicoId(contratoDTO)) {
			throw new DuplicationException("Contrato duplicado!");
		}
	}

	public void delete(Long id) {
		contratoRepository.deleteById(id);
	}

	public Page<ContratoListDTO> find(Pageable pageable, String cnpj) {
		return contratoRepository.findByCnpj(cnpj, pageable);
	}
}
