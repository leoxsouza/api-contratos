package leonardo.test.venturus.service.mapper;

import leonardo.test.venturus.domain.Cliente;
import leonardo.test.venturus.service.dto.ClienteDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ClienteMapper {

    Cliente toEntity(ClienteDTO dto);

    ClienteDTO toDto(Cliente entity);

}
