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
import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.gateways.AcompanhanteDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.AcompanhanteRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

public class AcompanhanteControllerTest extends IntegrationTest {

  private static final String URL = "/api/v1/hospedes/%s/acompanhantes";
  private static final String URL_WITH_PARAM = "/api/v1/hospedes/%s/acompanhantes/%s";

  @Autowired
  private WebApplicationContext webAppContext;

  @Autowired
  private HospedeDatabaseGateway hospedeDatabaseGateway;

  @Autowired
  private AcompanhanteDatabaseGateway acompanhanteDatabaseGateway;

  @Autowired
  private ObjectMapper objectMapper;

  private MockMvc mockMVC;

  @BeforeEach
  public void setUp() {
    mockMVC = webAppContextSetup(webAppContext).build();
  }

  @Test
  public void deveCriarUmAcompanhante() throws Exception {
    final Hospede hospede = hospedeDatabaseGateway.save(
        from(Hospede.class).gimme(VALIDO_SEM_ID.name()));
    final AcompanhanteRequest acompanhanteRequest =
        from(AcompanhanteRequest.class).gimme(VALIDO.name());
    mockMVC
        .perform(post(format(URL, hospede.getId()))
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(acompanhanteRequest)))
        .andExpect(status().isOk())
        .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.nome").value(acompanhanteRequest.getNome()))
        .andExpect(jsonPath("$.documento").value(acompanhanteRequest.getDocumento()))
        .andExpect(jsonPath("$.dataNascimento")
            .value(acompanhanteRequest.getDataNascimento().format(ISO_DATE)))
        .andExpect(jsonPath("$.ativo").value(acompanhanteRequest.isAtivo()));
  }

  @Test
  public void aoCriarUmAcompanhanteDeveValidarCamposObrigatorios() throws Exception {
    mockMVC
        .perform(post(format(URL, INTEGER_ONE)).contentType(APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erros", hasSize(2)))
        .andExpect(jsonPath("$.erros", hasItems("nome: must not be blank")))
        .andExpect(jsonPath("$.erros", hasItems("documento: must not be blank")));
  }

  @Test
  public void deveEditarUmAcompanhante() throws Exception {
    Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO_SEM_ID.name());
    final Hospede hospede = hospedeDatabaseGateway.save(acompanhante.getHospede());
    acompanhante.setHospede(hospede);
    acompanhante = acompanhanteDatabaseGateway.save(acompanhante);

    final AcompanhanteRequest acompanhanteRequest =
        from(AcompanhanteRequest.class).gimme(VALIDO.name());

    mockMVC
        .perform(put(format(URL_WITH_PARAM, hospede.getId(), acompanhante.getId()))
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(acompanhanteRequest)))
        .andExpect(status().isOk())
        .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.nome").value(acompanhanteRequest.getNome()))
        .andExpect(jsonPath("$.documento").value(acompanhanteRequest.getDocumento()))
        .andExpect(jsonPath("$.dataNascimento")
            .value(acompanhanteRequest.getDataNascimento().format(ISO_DATE)));
  }

  @Test
  public void aoEditarUmAcompanhanteDeveValidarCamposObrigatorios() throws Exception {
    mockMVC
        .perform(put(format(URL_WITH_PARAM, INTEGER_ONE, INTEGER_ONE))
            .contentType(APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erros", hasSize(2)))
        .andExpect(jsonPath("$.erros", hasItems("nome: must not be blank")))
        .andExpect(jsonPath("$.erros", hasItems("documento: must not be blank")));
  }

  @Test
  public void deveBuscarUmAcompanhante() throws Exception {

    Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO_SEM_ID.name());
    final Hospede hospede = hospedeDatabaseGateway.save(acompanhante.getHospede());
    acompanhante.setHospede(hospede);
    acompanhante = acompanhanteDatabaseGateway.save(acompanhante);

    mockMVC
        .perform(get(format(URL_WITH_PARAM, hospede.getId(), acompanhante.getId())).contentType(
            APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(acompanhante.getId()))
        .andExpect(jsonPath("$.nome").value(acompanhante.getNome()))
        .andExpect(jsonPath("$.documento").value(acompanhante.getDocumento()))
        .andExpect(jsonPath("$.dataNascimento")
            .value(acompanhante.getDataNascimento().format(ISO_DATE)));
  }

  @Test
  public void aoBuscarUmAcompanhanteDeveRetornar404QuandoNaoEncontrado() throws Exception {
    mockMVC
        .perform(
            get(format(URL_WITH_PARAM, INTEGER_ONE, INTEGER_ONE)).contentType(APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void devePesquisarUmHospedeSemFiltros() throws Exception {
    Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO_SEM_ID.name());
    final Hospede hospede = hospedeDatabaseGateway.save(acompanhante.getHospede());
    acompanhante.setHospede(hospede);
    acompanhante = acompanhanteDatabaseGateway.save(acompanhante);

    mockMVC
        .perform(get(format(URL, hospede.getId())).contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.elementos", hasSize(1)))
        .andExpect(jsonPath("$.elementos[0].id").value(acompanhante.getId()))
        .andExpect(jsonPath("$.elementos[0].nome").value(acompanhante.getNome()))
        .andExpect(jsonPath("$.elementos[0].documento").value(acompanhante.getDocumento()))
        .andExpect(jsonPath("$.elementos[0].dataNascimento")
            .value(acompanhante.getDataNascimento().format(ISO_DATE)))
        .andExpect(jsonPath("$.pagina").value(0))
        .andExpect(jsonPath("$.tamanho").value(20))
        .andExpect(jsonPath("$.totalPaginas").value(1))
        .andExpect(jsonPath("$.totalElementos").value(1));
  }

  @Test
  public void devePesquisarUmHospedePorNomeEDocumento() throws Exception {
    Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO_SEM_ID.name());
    final Acompanhante acompanhanteComOutroNome =
        from(Acompanhante.class).gimme(VALIDO_OUTRO_NOME_E_DOCUMENTO.name());

    final Hospede hospede = hospedeDatabaseGateway.save(acompanhante.getHospede());
    acompanhante.setHospede(hospede);
    acompanhanteComOutroNome.setHospede(hospede);

    acompanhante = acompanhanteDatabaseGateway.save(acompanhante);
    acompanhanteDatabaseGateway.save(acompanhanteComOutroNome);

    mockMVC
        .perform(get(format(URL, hospede.getId()))
            .param("nome", acompanhante.getNome())
            .param("documento", acompanhante.getDocumento())
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.elementos", hasSize(1)))
        .andExpect(jsonPath("$.elementos[0].id").value(acompanhante.getId()))
        .andExpect(jsonPath("$.elementos[0].nome").value(acompanhante.getNome()))
        .andExpect(jsonPath("$.elementos[0].documento").value(acompanhante.getDocumento()))
        .andExpect(jsonPath("$.elementos[0].dataNascimento")
            .value(acompanhante.getDataNascimento().format(ISO_DATE)))
        .andExpect(jsonPath("$.pagina").value(0))
        .andExpect(jsonPath("$.tamanho").value(20))
        .andExpect(jsonPath("$.totalPaginas").value(1))
        .andExpect(jsonPath("$.totalElementos").value(1));
  }
}