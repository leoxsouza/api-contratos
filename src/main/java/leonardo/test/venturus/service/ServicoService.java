package leonardo.test.venturus.service;

import leonardo.test.venturus.domain.Servico;
import leonardo.test.venturus.repository.ServicoRepository;
import leonardo.test.venturus.service.dto.ServicoDTO;
import leonardo.test.venturus.service.mapper.ServicoMapper;
import leonardo.test.venturus.service.util.exception.DuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicoService {

	private final ServicoRepository servicoRepository;

	private final ServicoMapper servicoMapper;

	public ServicoDTO save(ServicoDTO servicoDTO) {
		verifyExistence(servicoDTO);
		Servico servico = servicoMapper.toEntity(servicoDTO);
		return servicoMapper.toDto(servicoRepository.save(servico));
	}

	private void verifyExistence(ServicoDTO servicoDTO) {
		if (servicoRepository.existsByNome(servicoDTO.getNome(), servicoDTO.getId())) {
			throw new DuplicationException("Já existe um serviço cadastrado com este Nome!");
		}
	}

	public void delete(Long id) {
		servicoRepository.deleteById(id);
	}

	public Page<ServicoDTO> find(Pageable pageable, String nome) {
		return servicoRepository.findByNome(nome, pageable);
	}
}
