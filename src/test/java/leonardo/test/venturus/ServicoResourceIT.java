package leonardo.test.venturus;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import leonardo.test.venturus.domain.Servico;
import leonardo.test.venturus.repository.ServicoRepository;
import leonardo.test.venturus.service.ServicoService;
import leonardo.test.venturus.service.dto.ServicoDTO;
import leonardo.test.venturus.web.rest.ServicoResource;
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
public class ServicoResourceIT {

	@Autowired
	private ServicoService servicoService;

	private MockMvc mockMvc;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ServicoRepository servicoRepository;

	private static final String API = "/api/servico";

	@BeforeEach
	public void setup() {
		servicoRepository.deleteAll();

		ServicoResource resource = new ServicoResource(servicoService);
		this.mockMvc = MockMvcBuilders.standaloneSetup(resource)
			.setCustomArgumentResolvers(pageableArgumentResolver)
			.setMessageConverters(jacksonMessageConverter).build();
	}

	public static Long getSavedId(ApplicationContext context){
		Servico servico = new Servico();
		servico.setNome("Desenvolvimento de Sistema");
		servico.setDescricao("Sistema web em java");
		return context.getBean(ServicoRepository.class).save(servico).getId();
	}

	public ServicoDTO buildDefaultDTO() {
		ServicoDTO dto = new ServicoDTO();
		dto.setNome("Desenvolvimento de Sistema");
		dto.setDescricao("Sistema web em java");
		return dto;
	}

	public ServicoDTO postDTO(ServicoDTO dto) throws Exception {
		return jacksonMessageConverter.getObjectMapper().readValue(mockMvc.perform(post(API)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jacksonMessageConverter.getObjectMapper()
				.writeValueAsBytes(dto)))
			.andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), ServicoDTO.class);
	}

	@Test
	public void createEditAndFind() throws Exception {
		ServicoDTO dto = postDTO(buildDefaultDTO());
		assertNotNull(dto.getId());

		dto.setNome("Consultoria");
		dto = postDTO(dto);

		assertEquals("Consultoria", dto.getNome());
	}

	@Test
	public void delete() throws Exception {
		ServicoDTO dto = postDTO(buildDefaultDTO());
		mockMvc.perform(MockMvcRequestBuilders.delete(API + "/" + dto.getId())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void find() throws Exception {
		postDTO(buildDefaultDTO());
		ServicoDTO dto = new ServicoDTO();
		dto.setNome("Implantação");
		dto.setDescricao("Implantação de sistema em ambiente de produção");
		postDTO(dto);

		mockMvc.perform(get(API + "?nome=Implantação").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(jsonPath("$.content", Matchers.hasSize(1)))
			.andExpect(jsonPath("$.content[*].id", is(notNullValue())))
			.andExpect(jsonPath("$.content[*].descricao", is(notNullValue())))
			.andExpect(jsonPath("$.content[*].nome", is(notNullValue())));
	}

}
