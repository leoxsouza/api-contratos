package leonardo.test.venturus.web.rest;

import io.swagger.annotations.ApiParam;
import leonardo.test.venturus.service.ContratoService;
import leonardo.test.venturus.service.dto.ContratoDTO;
import leonardo.test.venturus.service.dto.ContratoListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/contrato")
@RequiredArgsConstructor
@Slf4j
public class ContratoResource {

    private final ContratoService contratoService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContratoDTO> save(@RequestBody ContratoDTO contratoDTO) {
        log.info("Request to save Contrato: {}", contratoDTO.toString());
        return new ResponseEntity<>(contratoService.save(contratoDTO), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request to delete Contrato: {}", id);
        contratoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ContratoListDTO>> findAll(@ApiParam Pageable pageable, @RequestParam(required = false) String cnpj) {
        log.info("Request to find Contrato by cnpj: {}", cnpj);
        return new ResponseEntity<>(contratoService.find(pageable, cnpj), HttpStatus.OK);
    }

}
