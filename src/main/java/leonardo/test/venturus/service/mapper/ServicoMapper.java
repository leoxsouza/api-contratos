package leonardo.test.venturus.service.mapper;

import leonardo.test.venturus.domain.Servico;
import leonardo.test.venturus.service.dto.ServicoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ServicoMapper {

    Servico toEntity(ServicoDTO dto);

    ServicoDTO toDto(Servico entity);

}
