package leonardo.test.venturus.service.mapper;

import leonardo.test.venturus.domain.Contrato;
import leonardo.test.venturus.service.dto.ContratoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface ContratoMapper {

    @Mapping(target = "cliente.id", source = "idCliente")
    @Mapping(target = "servico.id", source = "idServico")
    Contrato toEntity(ContratoDTO dto);

    @Mapping(source = "cliente.id", target = "idCliente")
    @Mapping(source = "servico.id", target = "idServico")
    ContratoDTO toDto(Contrato entity);

}
