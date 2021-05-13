package leonardo.test.venturus;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import leonardo.test.venturus.repository.ContratoRepository;
import leonardo.test.venturus.service.ContratoService;
import leonardo.test.venturus.service.dto.ContratoDTO;
import leonardo.test.venturus.service.dto.ServicoDTO;
import leonardo.test.venturus.web.rest.ContratoResource;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class ContratoResourceIT {

	@Autowired
	private ContratoService contratoService;

	private MockMvc mockMvc;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ContratoRepository contratoRepository;

	@Autowired
	private ApplicationContext context;

	private static final String API = "/api/contrato";

	@BeforeEach
	public void setup() {
		contratoRepository.deleteAll();

		ContratoResource resource = new ContratoResource(contratoService);
		this.mockMvc = MockMvcBuilders.standaloneSetup(resource)
			.setCustomArgumentResolvers(pageableArgumentResolver)
			.setMessageConverters(jacksonMessageConverter).build();
	}

	public ContratoDTO buildDefaultDTO() {
		ContratoDTO dto = new ContratoDTO();
		dto.setVigencia(LocalDate.now());
		dto.setIdCliente(ClienteResourceIT.getSavedId(context));
		dto.setIdServico(ServicoResourceIT.getSavedId(context));
		return dto;
	}

	public ContratoDTO postDTO(ContratoDTO dto) throws Exception {
		return jacksonMessageConverter.getObjectMapper().readValue(mockMvc.perform(post(API)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jacksonMessageConverter.getObjectMapper()
				.writeValueAsBytes(dto)))
			.andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), ContratoDTO.class);
	}

	@Test
	public void createEditAndFind() throws Exception {
		ContratoDTO dto = postDTO(buildDefaultDTO());
		assertNotNull(dto.getId());

		LocalDate vigenciaEditada = LocalDate.of(2021, 12, 12);

		dto.setVigencia(vigenciaEditada);
		dto = postDTO(dto);

		assertEquals(vigenciaEditada, dto.getVigencia());
	}

	@Test
	public void delete() throws Exception {
		ContratoDTO dto = postDTO(buildDefaultDTO());
		mockMvc.perform(MockMvcRequestBuilders.delete(API + "/" + dto.getId())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void find() throws Exception {
		postDTO(buildDefaultDTO());

		mockMvc.perform(get(API + "?cnpj=74759859000115").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(jsonPath("$.content", Matchers.hasSize(1)))
			.andExpect(jsonPath("$.content[*].id", is(notNullValue())))
			.andExpect(jsonPath("$.content[*].vigencia", is(notNullValue())))
			.andExpect(jsonPath("$.content[*].nomeCliente", is(notNullValue())))
			.andExpect(jsonPath("$.content[*].nomeServico", is(notNullValue())));
	}

}
