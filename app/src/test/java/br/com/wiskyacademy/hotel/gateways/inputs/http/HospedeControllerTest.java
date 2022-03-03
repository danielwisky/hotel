package br.com.wiskyacademy.hotel.gateways.inputs.http;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_OUTRO_NOME_E_DOCUMENTO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_SEM_ID;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ISO_DATE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import br.com.wiskyacademy.hotel.IntegrationTest;
import br.com.wiskyacademy.hotel.domains.Endereco;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.EnderecoRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.HospedeRequest;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.HospedeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

public class HospedeControllerTest extends IntegrationTest {

  private static final String URL = "/api/v1/hospedes";
  private static final String URL_WITH_PARAM = "/api/v1/hospedes/%s";

  @Autowired
  private WebApplicationContext webAppContext;

  @Autowired
  private HospedeRepository hospedeRepository;

  @Autowired
  private HospedeDatabaseGateway hospedeDatabaseGateway;

  @Autowired
  private ObjectMapper objectMapper;

  private MockMvc mockMVC;

  @BeforeEach
  public void setUp() {
    hospedeRepository.deleteAll();
    mockMVC = webAppContextSetup(webAppContext).build();
  }

  @Test
  public void deveCriarUmHospede() throws Exception {
    final HospedeRequest hospedeRequest = from(HospedeRequest.class).gimme(VALIDO.name());
    final EnderecoRequest enderecoRequest = hospedeRequest.getEndereco();
    mockMVC
        .perform(post(URL)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(hospedeRequest)))
        .andExpect(status().isOk())
        .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.nome").value(hospedeRequest.getNome()))
        .andExpect(jsonPath("$.documento").value(hospedeRequest.getDocumento()))
        .andExpect(jsonPath("$.dataNascimento")
            .value(hospedeRequest.getDataNascimento().format(ISO_DATE)))
        .andExpect(jsonPath("$.endereco.cep").value(enderecoRequest.getCep()))
        .andExpect(jsonPath("$.endereco.numero").value(enderecoRequest.getNumero()))
        .andExpect(jsonPath("$.endereco.bairro").value(enderecoRequest.getBairro()))
        .andExpect(jsonPath("$.endereco.cidade").value(enderecoRequest.getCidade()))
        .andExpect(jsonPath("$.endereco.estado").value(enderecoRequest.getEstado()))
        .andExpect(jsonPath("$.endereco.logradouro").value(enderecoRequest.getLogradouro()))
        .andExpect(jsonPath("$.endereco.complemento").value(enderecoRequest.getComplemento()))
        .andExpect(jsonPath("$.email").value(hospedeRequest.getEmail()))
        .andExpect(jsonPath("$.telefone").value(hospedeRequest.getTelefone()))
        .andExpect(jsonPath("$.celular").value(hospedeRequest.getCelular()));
  }

  @Test
  public void aoCriarUmHospedeDeveValidarCamposObrigatorios() throws Exception {
    mockMVC
        .perform(post(URL).contentType(APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erros", hasSize(4)))
        .andExpect(jsonPath("$.erros", hasItems("nome: must not be blank")))
        .andExpect(jsonPath("$.erros", hasItems("documento: must not be blank")))
        .andExpect(jsonPath("$.erros", hasItems("celular: must not be blank")))
        .andExpect(jsonPath("$.erros", hasItems("endereco: must not be null")));
  }

  @Test
  public void deveEditarUmHospede() throws Exception {
    final HospedeRequest hospedeRequest = from(HospedeRequest.class).gimme(VALIDO.name());
    final EnderecoRequest enderecoRequest = hospedeRequest.getEndereco();
    final Hospede hospede =
        hospedeDatabaseGateway.save(from(Hospede.class).gimme(VALIDO_SEM_ID.name()));

    mockMVC
        .perform(put(format(URL_WITH_PARAM, hospede.getId()))
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(hospedeRequest)))
        .andExpect(status().isOk())
        .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.nome").value(hospedeRequest.getNome()))
        .andExpect(jsonPath("$.documento").value(hospedeRequest.getDocumento()))
        .andExpect(jsonPath("$.dataNascimento")
            .value(hospedeRequest.getDataNascimento().format(ISO_DATE)))
        .andExpect(jsonPath("$.endereco.cep").value(enderecoRequest.getCep()))
        .andExpect(jsonPath("$.endereco.numero").value(enderecoRequest.getNumero()))
        .andExpect(jsonPath("$.endereco.bairro").value(enderecoRequest.getBairro()))
        .andExpect(jsonPath("$.endereco.cidade").value(enderecoRequest.getCidade()))
        .andExpect(jsonPath("$.endereco.estado").value(enderecoRequest.getEstado()))
        .andExpect(jsonPath("$.endereco.logradouro").value(enderecoRequest.getLogradouro()))
        .andExpect(jsonPath("$.endereco.complemento").value(enderecoRequest.getComplemento()))
        .andExpect(jsonPath("$.email").value(hospedeRequest.getEmail()))
        .andExpect(jsonPath("$.telefone").value(hospedeRequest.getTelefone()))
        .andExpect(jsonPath("$.celular").value(hospedeRequest.getCelular()));
  }

  @Test
  public void aoEditarUmHospedeDeveValidarCamposObrigatorios() throws Exception {
    mockMVC
        .perform(
            put(format(URL_WITH_PARAM, INTEGER_ONE)).contentType(APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erros", hasSize(4)))
        .andExpect(jsonPath("$.erros", hasItems("nome: must not be blank")))
        .andExpect(jsonPath("$.erros", hasItems("documento: must not be blank")))
        .andExpect(jsonPath("$.erros", hasItems("celular: must not be blank")))
        .andExpect(jsonPath("$.erros", hasItems("endereco: must not be null")));
  }

  @Test
  public void deveBuscarUmHospede() throws Exception {
    final Hospede hospede =
        hospedeDatabaseGateway.save(from(Hospede.class).gimme(VALIDO_SEM_ID.name()));
    final Endereco endereco = hospede.getEndereco();

    mockMVC
        .perform(get(format(URL_WITH_PARAM, hospede.getId())).contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(hospede.getId()))
        .andExpect(jsonPath("$.nome").value(hospede.getNome()))
        .andExpect(jsonPath("$.documento").value(hospede.getDocumento()))
        .andExpect(jsonPath("$.dataNascimento")
            .value(hospede.getDataNascimento().format(ISO_DATE)))
        .andExpect(jsonPath("$.endereco.cep").value(endereco.getCep()))
        .andExpect(jsonPath("$.endereco.numero").value(endereco.getNumero()))
        .andExpect(jsonPath("$.endereco.bairro").value(endereco.getBairro()))
        .andExpect(jsonPath("$.endereco.cidade").value(endereco.getCidade()))
        .andExpect(jsonPath("$.endereco.estado").value(endereco.getEstado()))
        .andExpect(jsonPath("$.endereco.logradouro").value(endereco.getLogradouro()))
        .andExpect(jsonPath("$.endereco.complemento").value(endereco.getComplemento()))
        .andExpect(jsonPath("$.email").value(hospede.getEmail()))
        .andExpect(jsonPath("$.telefone").value(hospede.getTelefone()))
        .andExpect(jsonPath("$.celular").value(hospede.getCelular()));
  }

  @Test
  public void aoBuscarUmHospedeDeveRetornar404QuandoNaoEncontrado() throws Exception {
    mockMVC
        .perform(get(format(URL_WITH_PARAM, INTEGER_ONE)).contentType(APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void devePesquisarUmHospedeSemFiltros() throws Exception {
    final Hospede hospede =
        hospedeDatabaseGateway.save(from(Hospede.class).gimme(VALIDO_SEM_ID.name()));
    final Endereco endereco = hospede.getEndereco();

    mockMVC
        .perform(get(format(URL)).contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.elementos", hasSize(1)))
        .andExpect(jsonPath("$.elementos[0].id").value(hospede.getId()))
        .andExpect(jsonPath("$.elementos[0].nome").value(hospede.getNome()))
        .andExpect(jsonPath("$.elementos[0].documento").value(hospede.getDocumento()))
        .andExpect(jsonPath("$.elementos[0].dataNascimento")
            .value(hospede.getDataNascimento().format(ISO_DATE)))
        .andExpect(jsonPath("$.elementos[0].endereco.cep").value(endereco.getCep()))
        .andExpect(jsonPath("$.elementos[0].endereco.numero").value(endereco.getNumero()))
        .andExpect(jsonPath("$.elementos[0].endereco.bairro").value(endereco.getBairro()))
        .andExpect(jsonPath("$.elementos[0].endereco.cidade").value(endereco.getCidade()))
        .andExpect(jsonPath("$.elementos[0].endereco.estado").value(endereco.getEstado()))
        .andExpect(jsonPath("$.elementos[0].endereco.logradouro").value(endereco.getLogradouro()))
        .andExpect(jsonPath("$.elementos[0].endereco.complemento").value(endereco.getComplemento()))
        .andExpect(jsonPath("$.elementos[0].email").value(hospede.getEmail()))
        .andExpect(jsonPath("$.elementos[0].telefone").value(hospede.getTelefone()))
        .andExpect(jsonPath("$.elementos[0].celular").value(hospede.getCelular()))
        .andExpect(jsonPath("$.pagina").value(0))
        .andExpect(jsonPath("$.tamanho").value(20))
        .andExpect(jsonPath("$.totalPaginas").value(1))
        .andExpect(jsonPath("$.totalElementos").value(1));
  }

  @Test
  public void devePesquisarUmHospedePorNomeEDocumento() throws Exception {
    hospedeDatabaseGateway.save(from(Hospede.class).gimme(VALIDO_OUTRO_NOME_E_DOCUMENTO.name()));
    final Hospede hospede =
        hospedeDatabaseGateway.save(from(Hospede.class).gimme(VALIDO_SEM_ID.name()));
    final Endereco endereco = hospede.getEndereco();

    mockMVC
        .perform(get(format(URL))
            .param("nome", hospede.getNome())
            .param("documento", hospede.getDocumento())
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.elementos", hasSize(1)))
        .andExpect(jsonPath("$.elementos[0].id").value(hospede.getId()))
        .andExpect(jsonPath("$.elementos[0].nome").value(hospede.getNome()))
        .andExpect(jsonPath("$.elementos[0].documento").value(hospede.getDocumento()))
        .andExpect(jsonPath("$.elementos[0].dataNascimento")
            .value(hospede.getDataNascimento().format(ISO_DATE)))
        .andExpect(jsonPath("$.elementos[0].endereco.cep").value(endereco.getCep()))
        .andExpect(jsonPath("$.elementos[0].endereco.numero").value(endereco.getNumero()))
        .andExpect(jsonPath("$.elementos[0].endereco.bairro").value(endereco.getBairro()))
        .andExpect(jsonPath("$.elementos[0].endereco.cidade").value(endereco.getCidade()))
        .andExpect(jsonPath("$.elementos[0].endereco.estado").value(endereco.getEstado()))
        .andExpect(jsonPath("$.elementos[0].endereco.logradouro").value(endereco.getLogradouro()))
        .andExpect(jsonPath("$.elementos[0].endereco.complemento").value(endereco.getComplemento()))
        .andExpect(jsonPath("$.elementos[0].email").value(hospede.getEmail()))
        .andExpect(jsonPath("$.elementos[0].telefone").value(hospede.getTelefone()))
        .andExpect(jsonPath("$.elementos[0].celular").value(hospede.getCelular()))
        .andExpect(jsonPath("$.pagina").value(0))
        .andExpect(jsonPath("$.tamanho").value(20))
        .andExpect(jsonPath("$.totalPaginas").value(1))
        .andExpect(jsonPath("$.totalElementos").value(1));
  }
}