package leonardo.test.venturus.service;

import leonardo.test.venturus.domain.Cliente;
import leonardo.test.venturus.repository.ClienteRepository;
import leonardo.test.venturus.service.dto.ClienteDTO;
import leonardo.test.venturus.service.mapper.ClienteMapper;
import leonardo.test.venturus.exception.DuplException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteService {

	private final ClienteRepository clienteRepository;

	private final ClienteMapper clienteMapper;

	public ClienteDTO save(ClienteDTO clienteDTO) throws DuplException {
		verifyExistence(clienteDTO);
		Cliente cliente = clienteMapper.toEntity(clienteDTO);
		return clienteMapper.toDto(clienteRepository.save(cliente));
	}

	private void verifyExistence(ClienteDTO clienteDTO) throws DuplException {
		if (clienteRepository.existsByCnpj(clienteDTO.getCnpj(), clienteDTO.getId())) {
			throw new DuplException(clienteDTO.getCnpj());
		}
	}

	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}

	public Page<ClienteDTO> find(Pageable pageable, String nome) {
		return clienteRepository.findByNome(nome, pageable);
	}
}
