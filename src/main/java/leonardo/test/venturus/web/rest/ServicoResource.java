package leonardo.test.venturus.web.rest;

import io.swagger.annotations.ApiParam;
import leonardo.test.venturus.service.ServicoService;
import leonardo.test.venturus.service.dto.ServicoDTO;
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
@RequestMapping(value = "/api/servico")
@RequiredArgsConstructor
@Slf4j
public class ServicoResource {

    private final ServicoService servicoService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServicoDTO> save(@RequestBody ServicoDTO servicoDTO) {
        log.info("Request to save Servico: {}", servicoDTO.toString());
        return new ResponseEntity<>(servicoService.save(servicoDTO), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request to delete Servico: {}", id);
        servicoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ServicoDTO>> findAll(@ApiParam Pageable pageable, @RequestParam(required = false) String nome) {
        log.info("Request to find Servico by name: {}", nome);
        return new ResponseEntity<>(servicoService.find(pageable, nome), HttpStatus.OK);
    }

}
