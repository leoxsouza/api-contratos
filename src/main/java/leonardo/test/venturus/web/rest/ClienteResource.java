package leonardo.test.venturus.web.rest;

import javax.validation.Valid;

import io.swagger.annotations.ApiParam;
import leonardo.test.venturus.service.ClienteService;
import leonardo.test.venturus.service.dto.ClienteDTO;
import leonardo.test.venturus.exception.DuplException;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/cliente")
@RequiredArgsConstructor
@Slf4j
public class ClienteResource {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO save(@RequestBody @Valid ClienteDTO clienteDTO) throws DuplException {
        log.info("Request to save Cliente: {}", clienteDTO.toString());
        return clienteService.save(clienteDTO);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request to delete Cliente: {}", id);
        clienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ClienteDTO>> findAll(@ApiParam Pageable pageable, @RequestParam(required = false) String nome) {
        log.info("Request to find Cliente by name: {}", nome);
        return new ResponseEntity<>(clienteService.find(pageable, nome), HttpStatus.OK);
    }

}
