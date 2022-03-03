package br.com.wiskyacademy.hotel.gateways.inputs.http;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.DESCRICAO;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.NOME;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_CAPACIDADE_ALTA;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_CAPACIDADE_BAIXA;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_OUTRO_NOME_E_DESCRICAO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_PRECO_ALTO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_PRECO_BAIXO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_SEM_ID;
import static java.lang.String.format;
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
import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.gateways.AcomodacaoDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.AcomodacaoRequest;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.AcomodacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

public class AcomodacaoControllerTest extends IntegrationTest {

  private static final String URL = "/api/v1/acomodacoes";
  private static final String URL_WITH_PARAM = "/api/v1/acomodacoes/%s";

  @Autowired
  private WebApplicationContext webAppContext;

  @Autowired
  private AcomodacaoRepository acomodacaoRepository;

  @Autowired
  private AcomodacaoDatabaseGateway acomodacaoDatabaseGateway;

  @Autowired
  private ObjectMapper objectMapper;

  private MockMvc mockMVC;

  @BeforeEach
  public void setUp() {
    acomodacaoRepository.deleteAll();
    mockMVC = webAppContextSetup(webAppContext).build();
  }

  @Test
  public void deveCriarUmaAcomodacao() throws Exception {
    final AcomodacaoRequest acomodacaoRequest = from(AcomodacaoRequest.class).gimme(VALIDO.name());
    mockMVC
        .perform(post(URL)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(acomodacaoRequest)))
        .andExpect(status().isOk())
        .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.nome").value(acomodacaoRequest.getNome()))
        .andExpect(jsonPath("$.descricao").value(acomodacaoRequest.getDescricao()))
        .andExpect(jsonPath("$.capacidade").value(acomodacaoRequest.getCapacidade()))
        .andExpect(jsonPath("$.preco").value(acomodacaoRequest.getPreco()));
  }

  @Test
  public void aoCriarUmaAcomodacaoDeveValidarCamposObrigatorios() throws Exception {
    mockMVC
        .perform(post(URL).contentType(APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erros", hasSize(3)))
        .andExpect(jsonPath("$.erros", hasItems("preco: must not be null")))
        .andExpect(jsonPath("$.erros", hasItems("capacidade: must not be null")))
        .andExpect(jsonPath("$.erros", hasItems("nome: must not be blank")));
  }

  @Test
  public void deveEditarUmaAcomodacao() throws Exception {
    final AcomodacaoRequest acomodacaoRequest = from(AcomodacaoRequest.class).gimme(VALIDO.name());
    final Acomodacao acomodacao =
        acomodacaoDatabaseGateway.save(from(Acomodacao.class).gimme(VALIDO_SEM_ID.name()));

    mockMVC
        .perform(put(format(URL_WITH_PARAM, acomodacao.getId()))
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(acomodacaoRequest)))
        .andExpect(status().isOk())
        .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.nome").value(acomodacaoRequest.getNome()))
        .andExpect(jsonPath("$.descricao").value(acomodacaoRequest.getDescricao()))
        .andExpect(jsonPath("$.capacidade").value(acomodacaoRequest.getCapacidade()))
        .andExpect(jsonPath("$.preco").value(acomodacaoRequest.getPreco()));
  }

  @Test
  public void aoEditarUmaAcomodacaoDeveValidarCamposObrigatorios() throws Exception {
    mockMVC
        .perform(
            put(format(URL_WITH_PARAM, INTEGER_ONE)).contentType(APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erros", hasSize(3)))
        .andExpect(jsonPath("$.erros", hasItems("preco: must not be null")))
        .andExpect(jsonPath("$.erros", hasItems("capacidade: must not be null")))
        .andExpect(jsonPath("$.erros", hasItems("nome: must not be blank")));
  }

  @Test
  public void aoEditarUmaAcomodacaoDeveRetornar404QuandoNaoEncontrado() throws Exception {
    final AcomodacaoRequest acomodacaoRequest = from(AcomodacaoRequest.class).gimme(VALIDO.name());
    mockMVC
        .perform(put(format(URL_WITH_PARAM, INTEGER_ONE))
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(acomodacaoRequest)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void deveBuscarUmaAcomodacao() throws Exception {
    final Acomodacao acomodacao =
        acomodacaoDatabaseGateway.save(from(Acomodacao.class).gimme(VALIDO_SEM_ID.name()));

    mockMVC
        .perform(get(format(URL_WITH_PARAM, acomodacao.getId())).contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(acomodacao.getId()))
        .andExpect(jsonPath("$.nome").value(acomodacao.getNome()))
        .andExpect(jsonPath("$.descricao").value(acomodacao.getDescricao()))
        .andExpect(jsonPath("$.capacidade").value(acomodacao.getCapacidade()))
        .andExpect(jsonPath("$.preco").value(acomodacao.getPreco()));
  }

  @Test
  public void aoBuscarUmaAcomodacaoDeveRetornar404QuandoNaoEncontrado() throws Exception {
    mockMVC
        .perform(get(format(URL_WITH_PARAM, INTEGER_ONE)).contentType(APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void devePesquisarUmaAcomodacaoSemFiltros() throws Exception {
    final Acomodacao acomodacao =
        acomodacaoDatabaseGateway.save(from(Acomodacao.class).gimme(VALIDO_SEM_ID.name()));

    mockMVC
        .perform(get(format(URL)).contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.elementos", hasSize(1)))
        .andExpect(jsonPath("$.elementos[0].id").value(acomodacao.getId()))
        .andExpect(jsonPath("$.elementos[0].nome").value(acomodacao.getNome()))
        .andExpect(jsonPath("$.elementos[0].descricao").value(acomodacao.getDescricao()))
        .andExpect(jsonPath("$.elementos[0].capacidade").value(acomodacao.getCapacidade()))
        .andExpect(jsonPath("$.elementos[0].preco").value(acomodacao.getPreco()))
        .andExpect(jsonPath("$.pagina").value(0))
        .andExpect(jsonPath("$.tamanho").value(20))
        .andExpect(jsonPath("$.totalPaginas").value(1))
        .andExpect(jsonPath("$.totalElementos").value(1));
  }

  @Test
  public void devePesquisarUmaAcomodacaoPorNomeEDescricao() throws Exception {

    acomodacaoDatabaseGateway.save(
        from(Acomodacao.class).gimme(VALIDO_OUTRO_NOME_E_DESCRICAO.name()));
    final Acomodacao acomodacao =
        acomodacaoDatabaseGateway.save(from(Acomodacao.class).gimme(VALIDO_SEM_ID.name()));

    mockMVC
        .perform(get(format(URL))
            .param(NOME, acomodacao.getNome())
            .param(DESCRICAO, acomodacao.getDescricao())
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.elementos", hasSize(1)))
        .andExpect(jsonPath("$.elementos[0].id").value(acomodacao.getId()))
        .andExpect(jsonPath("$.elementos[0].nome").value(acomodacao.getNome()))
        .andExpect(jsonPath("$.elementos[0].descricao").value(acomodacao.getDescricao()))
        .andExpect(jsonPath("$.elementos[0].capacidade").value(acomodacao.getCapacidade()))
        .andExpect(jsonPath("$.elementos[0].preco").value(acomodacao.getPreco()))
        .andExpect(jsonPath("$.pagina").value(0))
        .andExpect(jsonPath("$.tamanho").value(20))
        .andExpect(jsonPath("$.totalPaginas").value(1))
        .andExpect(jsonPath("$.totalElementos").value(1));
  }

  @Test
  public void devePesquisarUmaAcomodacaoPorPreco() throws Exception {

    acomodacaoDatabaseGateway.save(from(Acomodacao.class).gimme(VALIDO_PRECO_BAIXO.name()));
    final Acomodacao acomodacao =
        acomodacaoDatabaseGateway.save(from(Acomodacao.class).gimme(VALIDO_PRECO_ALTO.name()));

    mockMVC
        .perform(get(format(URL))
            .param("precoMaiorQue", acomodacao.getPreco().toString())
            .param("precoMenorQue", acomodacao.getPreco().toString())
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.elementos", hasSize(1)))
        .andExpect(jsonPath("$.elementos[0].id").value(acomodacao.getId()))
        .andExpect(jsonPath("$.elementos[0].nome").value(acomodacao.getNome()))
        .andExpect(jsonPath("$.elementos[0].descricao").value(acomodacao.getDescricao()))
        .andExpect(jsonPath("$.elementos[0].capacidade").value(acomodacao.getCapacidade()))
        .andExpect(jsonPath("$.elementos[0].preco").value(acomodacao.getPreco()))
        .andExpect(jsonPath("$.pagina").value(0))
        .andExpect(jsonPath("$.tamanho").value(20))
        .andExpect(jsonPath("$.totalPaginas").value(1))
        .andExpect(jsonPath("$.totalElementos").value(1));
  }

  @Test
  public void devePesquisarUmaAcomodacaoPorCapacidade() throws Exception {

    acomodacaoDatabaseGateway.save(from(Acomodacao.class).gimme(VALIDO_CAPACIDADE_BAIXA.name()));
    final Acomodacao acomodacao =
        acomodacaoDatabaseGateway.save(from(Acomodacao.class).gimme(VALIDO_CAPACIDADE_ALTA.name()));

    mockMVC
        .perform(get(format(URL))
            .param("capacidadeMaiorQue", acomodacao.getCapacidade().toString())
            .param("capacidadeMenorQue", acomodacao.getCapacidade().toString())
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.elementos", hasSize(1)))
        .andExpect(jsonPath("$.elementos[0].id").value(acomodacao.getId()))
        .andExpect(jsonPath("$.elementos[0].nome").value(acomodacao.getNome()))
        .andExpect(jsonPath("$.elementos[0].descricao").value(acomodacao.getDescricao()))
        .andExpect(jsonPath("$.elementos[0].capacidade").value(acomodacao.getCapacidade()))
        .andExpect(jsonPath("$.elementos[0].preco").value(acomodacao.getPreco()))
        .andExpect(jsonPath("$.pagina").value(0))
        .andExpect(jsonPath("$.tamanho").value(20))
        .andExpect(jsonPath("$.totalPaginas").value(1))
        .andExpect(jsonPath("$.totalElementos").value(1));
  }
}
