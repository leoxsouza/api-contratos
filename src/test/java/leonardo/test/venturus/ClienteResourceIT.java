package leonardo.test.venturus;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import leonardo.test.venturus.domain.Cliente;
import leonardo.test.venturus.repository.ClienteRepository;
import leonardo.test.venturus.service.ClienteService;
import leonardo.test.venturus.service.dto.ClienteDTO;
import leonardo.test.venturus.web.rest.ClienteResource;
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
public class ClienteResourceIT {

	@Autowired
	private ClienteService clienteService;

	private MockMvc mockMvc;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ClienteRepository clienteRepository;

	private static final String API = "/api/cliente";

	@BeforeEach
	public void setup() {
		clienteRepository.deleteAll();

		ClienteResource resource = new ClienteResource(clienteService);
		this.mockMvc = MockMvcBuilders.standaloneSetup(resource)
			.setCustomArgumentResolvers(pageableArgumentResolver)
			.setMessageConverters(jacksonMessageConverter).build();
	}

	public static Long getSavedId(ApplicationContext context){
		Cliente cliente = new Cliente();
		cliente.setCnpj("74759859000115");
		cliente.setNome("Microsoft");

		return context.getBean(ClienteRepository.class).save(cliente).getId();
	}

	public ClienteDTO buildDefaultDTO() {
		ClienteDTO dto = new ClienteDTO();
		dto.setCnpj("74759859000115");
		dto.setNome("Microsoft");
		return dto;
	}

	public ClienteDTO postDTO(ClienteDTO dto) throws Exception {
		return jacksonMessageConverter.getObjectMapper().readValue(mockMvc.perform(post(API)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jacksonMessageConverter.getObjectMapper()
				.writeValueAsBytes(dto)))
			.andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), ClienteDTO.class);
	}

	@Test
	public void createEditAndFind() throws Exception {
		ClienteDTO dto = postDTO(buildDefaultDTO());
		assertNotNull(dto.getId());

		dto.setNome("Google");
		dto = postDTO(dto);

		assertEquals("Google", dto.getNome());
	}

	@Test
	public void delete() throws Exception {
		ClienteDTO dto = postDTO(buildDefaultDTO());
		mockMvc.perform(MockMvcRequestBuilders.delete(API + "/" + dto.getId())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void find() throws Exception {
		postDTO(buildDefaultDTO());
		ClienteDTO dto = new ClienteDTO();
		dto.setNome("Google");
		dto.setCnpj("99674611000182");
		postDTO(dto);

		mockMvc.perform(get(API + "?nome=Google").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(jsonPath("$.content", Matchers.hasSize(1)))
			.andExpect(jsonPath("$.content[*].id", is(notNullValue())))
			.andExpect(jsonPath("$.content[*].cnpj", is(notNullValue())))
			.andExpect(jsonPath("$.content[*].nome", is(notNullValue())));
	}

}
