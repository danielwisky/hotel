package br.com.wiskyacademy.hotel.gateways.inputs.http;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_CAPACIDADE_BAIXA;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_SEM_ID;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import br.com.wiskyacademy.hotel.IntegrationTest;
import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.Endereco;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.StatusHospedagem;
import br.com.wiskyacademy.hotel.gateways.AcomodacaoDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.AcompanhanteDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.ReservaHospedagemRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

public class HospedagemControllerTest extends IntegrationTest {

  private static final String URL = "/api/v1/hospedagens";
  private static final String URL_WITH_PARAM = "/api/v1/hospedagens/%s";
  private static final String URL_CHECK_IN = "/api/v1/hospedagens/%s/check-in";
  private static final String URL_CHECK_OUT = "/api/v1/hospedagens/%s/check-out";


  @Autowired
  private WebApplicationContext webAppContext;

  @Autowired
  private HospedagemDatabaseGateway hospedagemDatabaseGateway;

  @Autowired
  private HospedeDatabaseGateway hospedeDatabaseGateway;

  @Autowired
  private AcomodacaoDatabaseGateway acomodacaoDatabaseGateway;

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
  public void deveCriarUmaHospedagem() throws Exception {
    Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO_SEM_ID.name());
    acompanhante.setHospede(hospedeDatabaseGateway.save(acompanhante.getHospede()));
    acompanhante = acompanhanteDatabaseGateway.save(acompanhante);
    final Acomodacao acomodacao = acomodacaoDatabaseGateway.save(
        from(Acomodacao.class).gimme(VALIDO_SEM_ID.name()));

    final ReservaHospedagemRequest reservaHospedagemRequest = new ReservaHospedagemRequest();
    reservaHospedagemRequest.setHospede(acompanhante.getHospede().getId());
    reservaHospedagemRequest.setAcomodacao(acomodacao.getId());
    reservaHospedagemRequest.setAcompanhantes(Arrays.asList(acompanhante.getId()));
    reservaHospedagemRequest.setDataEntrada(LocalDate.of(2022, 3, 20));
    reservaHospedagemRequest.setDataSaida(LocalDate.of(2022, 3, 26));

    mockMVC
        .perform(post(URL)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reservaHospedagemRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.acomodacao.id").value(reservaHospedagemRequest.getAcomodacao()))
        .andExpect(jsonPath("$.hospede.id").value(reservaHospedagemRequest.getHospede()))
        .andExpect(
            jsonPath("$.acompanhantes", hasSize(reservaHospedagemRequest.getAcompanhantes().size())))
        .andExpect(
            jsonPath("$.dataEntrada").value(reservaHospedagemRequest.getDataEntrada().format(ISO_DATE)))
        .andExpect(jsonPath("$.dataSaida").value(reservaHospedagemRequest.getDataSaida().format(ISO_DATE)))
        .andExpect(jsonPath("$.status").value(StatusHospedagem.RESERVADO.name()));
  }

  @Test
  public void aoCriarUmaHospedagemDeveValidarCamposObrigatorios() throws Exception {
    mockMVC
        .perform(post(URL).contentType(APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erros", hasSize(4)))
        .andExpect(jsonPath("$.erros", hasItems("acomodacao: must not be null")))
        .andExpect(jsonPath("$.erros", hasItems("dataEntrada: must not be null")))
        .andExpect(jsonPath("$.erros", hasItems("dataSaida: must not be null")))
        .andExpect(jsonPath("$.erros", hasItems("hospede: must not be null")));
  }

  @Test
  public void aoCriarUmaHospedagemDeveValidarCapacidadeAcomodacao() throws Exception {
    Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO_SEM_ID.name());
    acompanhante.setHospede(hospedeDatabaseGateway.save(acompanhante.getHospede()));
    acompanhante = acompanhanteDatabaseGateway.save(acompanhante);
    final Acomodacao acomodacao = acomodacaoDatabaseGateway.save(
        from(Acomodacao.class).gimme(VALIDO_CAPACIDADE_BAIXA.name()));

    final ReservaHospedagemRequest reservaHospedagemRequest = new ReservaHospedagemRequest();
    reservaHospedagemRequest.setHospede(acompanhante.getHospede().getId());
    reservaHospedagemRequest.setAcomodacao(acomodacao.getId());
    reservaHospedagemRequest.setAcompanhantes(Arrays.asList(acompanhante.getId()));
    reservaHospedagemRequest.setDataEntrada(LocalDate.of(2022, 3, 20));
    reservaHospedagemRequest.setDataSaida(LocalDate.of(2022, 3, 26));

    mockMVC
        .perform(post(URL)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reservaHospedagemRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erros", hasSize(1)))
        .andExpect(jsonPath("$.erros", hasItems(
            String.format("A capacidade máxima nesta acomodação é de %s hóspede(s).",
                acomodacao.getCapacidade()))));
  }

  @Test
  public void aoCriarUmaHospedagemDeveValidarDisponibilidadeDataEntrada() throws Exception {
    Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO_SEM_ID.name());
    final Hospede hospede = hospedeDatabaseGateway.save(acompanhante.getHospede());
    acompanhante.setHospede(hospede);
    acompanhante = acompanhanteDatabaseGateway.save(acompanhante);
    final Acomodacao acomodacao = acomodacaoDatabaseGateway.save(
        from(Acomodacao.class).gimme(VALIDO_SEM_ID.name()));

    final Hospedagem hospedagem = new Hospedagem();
    hospedagem.setAcomodacao(acomodacao);
    hospedagem.setAcompanhantes(Arrays.asList(acompanhante));
    hospedagem.setHospede(hospede);
    hospedagem.setDataEntrada(LocalDate.of(2022, 3, 20));
    hospedagem.setDataSaida(LocalDate.of(2022, 4, 20));
    hospedagem.setStatus(StatusHospedagem.PAGO);
    hospedagem.setValor(200f);
    hospedagemDatabaseGateway.save(hospedagem);

    final ReservaHospedagemRequest reservaHospedagemRequest = new ReservaHospedagemRequest();
    reservaHospedagemRequest.setHospede(hospede.getId());
    reservaHospedagemRequest.setAcomodacao(acomodacao.getId());
    reservaHospedagemRequest.setAcompanhantes(Arrays.asList(acompanhante.getId()));
    reservaHospedagemRequest.setDataEntrada(LocalDate.of(2022, 3, 20));
    reservaHospedagemRequest.setDataSaida(LocalDate.of(2022, 3, 26));

    mockMVC
        .perform(post(URL)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reservaHospedagemRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erros", hasSize(1)))
        .andExpect(jsonPath("$.erros", hasItems(
            String.format("Acomodação indisponível para o período selecionado.",
                acomodacao.getCapacidade()))));
  }

  @Test
  public void aoCriarUmaHospedagemDeveValidarDisponibilidadeDataSaida() throws Exception {
    Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO_SEM_ID.name());
    final Hospede hospede = hospedeDatabaseGateway.save(acompanhante.getHospede());
    acompanhante.setHospede(hospede);
    acompanhante = acompanhanteDatabaseGateway.save(acompanhante);
    final Acomodacao acomodacao = acomodacaoDatabaseGateway.save(
        from(Acomodacao.class).gimme(VALIDO_SEM_ID.name()));

    final Hospedagem hospedagem = new Hospedagem();
    hospedagem.setAcomodacao(acomodacao);
    hospedagem.setAcompanhantes(Arrays.asList(acompanhante));
    hospedagem.setHospede(hospede);
    hospedagem.setDataEntrada(LocalDate.of(2022, 1, 10));
    hospedagem.setDataSaida(LocalDate.of(2022, 3, 26));
    hospedagem.setStatus(StatusHospedagem.PAGO);
    hospedagem.setValor(200f);
    hospedagemDatabaseGateway.save(hospedagem);

    final ReservaHospedagemRequest reservaHospedagemRequest = new ReservaHospedagemRequest();
    reservaHospedagemRequest.setHospede(hospede.getId());
    reservaHospedagemRequest.setAcomodacao(acomodacao.getId());
    reservaHospedagemRequest.setAcompanhantes(Arrays.asList(acompanhante.getId()));
    reservaHospedagemRequest.setDataEntrada(LocalDate.of(2022, 3, 20));
    reservaHospedagemRequest.setDataSaida(LocalDate.of(2022, 3, 26));

    mockMVC
        .perform(post(URL)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reservaHospedagemRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erros", hasSize(1)))
        .andExpect(jsonPath("$.erros", hasItems(
            String.format("Acomodação indisponível para o período selecionado.",
                acomodacao.getCapacidade()))));
  }

  @Test
  public void aoCriarUmaHospedagemDeveValidarDisponibilidade() throws Exception {
    Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO_SEM_ID.name());
    final Hospede hospede = hospedeDatabaseGateway.save(acompanhante.getHospede());
    acompanhante.setHospede(hospede);
    acompanhante = acompanhanteDatabaseGateway.save(acompanhante);
    final Acomodacao acomodacao = acomodacaoDatabaseGateway.save(
        from(Acomodacao.class).gimme(VALIDO_SEM_ID.name()));

    final Hospedagem hospedagem = new Hospedagem();
    hospedagem.setAcomodacao(acomodacao);
    hospedagem.setAcompanhantes(Arrays.asList(acompanhante));
    hospedagem.setHospede(hospede);
    hospedagem.setDataEntrada(LocalDate.of(2022, 3, 21));
    hospedagem.setDataSaida(LocalDate.of(2022, 3, 25));
    hospedagem.setStatus(StatusHospedagem.PAGO);
    hospedagem.setValor(200f);
    hospedagemDatabaseGateway.save(hospedagem);

    final ReservaHospedagemRequest reservaHospedagemRequest = new ReservaHospedagemRequest();
    reservaHospedagemRequest.setHospede(hospede.getId());
    reservaHospedagemRequest.setAcomodacao(acomodacao.getId());
    reservaHospedagemRequest.setAcompanhantes(Arrays.asList(acompanhante.getId()));
    reservaHospedagemRequest.setDataEntrada(LocalDate.of(2022, 3, 20));
    reservaHospedagemRequest.setDataSaida(LocalDate.of(2022, 3, 26));

    mockMVC
        .perform(post(URL)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reservaHospedagemRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.erros", hasSize(1)))
        .andExpect(jsonPath("$.erros", hasItems(
            String.format("Acomodação indisponível para o período selecionado.",
                acomodacao.getCapacidade()))));
  }

  @Test
  public void deveRealizarCheckIn() throws Exception {
    final Hospedagem hospedagem = carregarHospedagem();

    mockMVC
        .perform(put(format(URL_CHECK_IN, hospedagem.getId())).contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.acomodacao.id").value(hospedagem.getAcomodacao().getId()))
        .andExpect(jsonPath("$.hospede.id").value(hospedagem.getHospede().getId()))
        .andExpect(jsonPath("$.acompanhantes", hasSize(hospedagem.getAcompanhantes().size())))
        .andExpect(jsonPath("$.dataEntrada").value(hospedagem.getDataEntrada().format(ISO_DATE)))
        .andExpect(jsonPath("$.dataSaida").value(hospedagem.getDataSaida().format(ISO_DATE)))
        .andExpect(jsonPath("$.status").value(hospedagem.getStatus().name()));
  }

  @Test
  public void deveRealizarCheckOut() throws Exception {
    Hospedagem hospedagem = carregarHospedagem();
    hospedagem.setDataCheckIn(LocalDateTime.now());
    hospedagem = hospedagemDatabaseGateway.save(hospedagem);

    mockMVC
        .perform(put(format(URL_CHECK_OUT, hospedagem.getId())).contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.acomodacao.id").value(hospedagem.getAcomodacao().getId()))
        .andExpect(jsonPath("$.hospede.id").value(hospedagem.getHospede().getId()))
        .andExpect(jsonPath("$.acompanhantes", hasSize(hospedagem.getAcompanhantes().size())))
        .andExpect(jsonPath("$.dataEntrada").value(hospedagem.getDataEntrada().format(ISO_DATE)))
        .andExpect(jsonPath("$.dataSaida").value(hospedagem.getDataSaida().format(ISO_DATE)))
        .andExpect(jsonPath("$.status").value(hospedagem.getStatus().name()));
  }

  @Test
  public void deveBuscarUmaHospedagem() throws Exception {
    final Hospedagem hospedagem = carregarHospedagem();
    final Acomodacao acomodacao = hospedagem.getAcomodacao();
    final Hospede hospede = hospedagem.getHospede();
    final Endereco endereco = hospede.getEndereco();

    mockMVC
        .perform(get(format(URL_WITH_PARAM, hospedagem.getId())).contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(hospedagem.getId()))
        .andExpect(jsonPath("$.acomodacao.id").value(acomodacao.getId()))
        .andExpect(jsonPath("$.acomodacao.nome").value(acomodacao.getNome()))
        .andExpect(jsonPath("$.acomodacao.descricao").value(acomodacao.getDescricao()))
        .andExpect(jsonPath("$.acomodacao.capacidade").value(acomodacao.getCapacidade()))
        .andExpect(jsonPath("$.acomodacao.preco").value(acomodacao.getPreco()))
        .andExpect(jsonPath("$.hospede.nome").value(hospede.getNome()))
        .andExpect(jsonPath("$.hospede.documento").value(hospede.getDocumento()))
        .andExpect(jsonPath("$.hospede.dataNascimento")
            .value(hospede.getDataNascimento().format(ISO_DATE)))
        .andExpect(jsonPath("$.hospede.endereco.cep").value(endereco.getCep()))
        .andExpect(jsonPath("$.hospede.endereco.numero").value(endereco.getNumero()))
        .andExpect(jsonPath("$.hospede.endereco.bairro").value(endereco.getBairro()))
        .andExpect(jsonPath("$.hospede.endereco.cidade").value(endereco.getCidade()))
        .andExpect(jsonPath("$.hospede.endereco.estado").value(endereco.getEstado()))
        .andExpect(jsonPath("$.hospede.endereco.logradouro").value(endereco.getLogradouro()))
        .andExpect(jsonPath("$.hospede.endereco.complemento").value(endereco.getComplemento()))
        .andExpect(jsonPath("$.hospede.email").value(hospede.getEmail()))
        .andExpect(jsonPath("$.hospede.telefone").value(hospede.getTelefone()))
        .andExpect(jsonPath("$.hospede.celular").value(hospede.getCelular()))
        .andExpect(jsonPath("$.acompanhantes", hasSize(hospedagem.getAcompanhantes().size())))
        .andExpect(jsonPath("$.dataEntrada").value(hospedagem.getDataEntrada().format(ISO_DATE)))
        .andExpect(jsonPath("$.dataSaida").value(hospedagem.getDataSaida().format(ISO_DATE)))
        .andExpect(jsonPath("$.status").value(hospedagem.getStatus().name()));
  }

  @Test
  public void aoBuscarUmaHospedagemDeveRetornar404QuandoNaoEncontrado() throws Exception {
    mockMVC
        .perform(get(format(URL_WITH_PARAM, INTEGER_ONE)).contentType(APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void devePesquisarUmaHospedagemSemFiltros() throws Exception {
    final Hospedagem hospedagem = carregarHospedagem();
    final Acomodacao acomodacao = hospedagem.getAcomodacao();
    final Hospede hospede = hospedagem.getHospede();
    final Endereco endereco = hospede.getEndereco();

    mockMVC
        .perform(get(format(URL)).contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.elementos", hasSize(1)))
        .andExpect(jsonPath("$.elementos[0].id").value(hospedagem.getId()))
        .andExpect(jsonPath("$.elementos[0].acomodacao.id").value(acomodacao.getId()))
        .andExpect(jsonPath("$.elementos[0].acomodacao.nome").value(acomodacao.getNome()))
        .andExpect(jsonPath("$.elementos[0].acomodacao.descricao").value(acomodacao.getDescricao()))
        .andExpect(jsonPath("$.elementos[0].acomodacao.capacidade")
            .value(acomodacao.getCapacidade()))
        .andExpect(jsonPath("$.elementos[0].acomodacao.preco").value(acomodacao.getPreco()))
        .andExpect(jsonPath("$.elementos[0].hospede.nome").value(hospede.getNome()))
        .andExpect(jsonPath("$.elementos[0].hospede.documento").value(hospede.getDocumento()))
        .andExpect(jsonPath("$.elementos[0].hospede.dataNascimento")
            .value(hospede.getDataNascimento().format(ISO_DATE)))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.cep").value(endereco.getCep()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.numero").value(endereco.getNumero()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.bairro").value(endereco.getBairro()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.cidade").value(endereco.getCidade()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.estado").value(endereco.getEstado()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.logradouro")
            .value(endereco.getLogradouro()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.complemento")
            .value(endereco.getComplemento()))
        .andExpect(jsonPath("$.elementos[0].hospede.email").value(hospede.getEmail()))
        .andExpect(jsonPath("$.elementos[0].hospede.telefone").value(hospede.getTelefone()))
        .andExpect(jsonPath("$.elementos[0].hospede.celular").value(hospede.getCelular()))
        .andExpect(
            jsonPath("$.elementos[0].acompanhantes", hasSize(hospedagem.getAcompanhantes().size())))
        .andExpect(jsonPath("$.elementos[0].dataEntrada")
            .value(hospedagem.getDataEntrada().format(ISO_DATE)))
        .andExpect(jsonPath("$.elementos[0].dataSaida")
            .value(hospedagem.getDataSaida().format(ISO_DATE)))
        .andExpect(jsonPath("$.elementos[0].status").value(hospedagem.getStatus().name()))
        .andExpect(jsonPath("$.pagina").value(0))
        .andExpect(jsonPath("$.tamanho").value(20))
        .andExpect(jsonPath("$.totalPaginas").value(1))
        .andExpect(jsonPath("$.totalElementos").value(1));
  }

  @Test
  public void devePesquisarUmaHospedagemPorAcomodacaoEHospedagemEStatus() throws Exception {
    final Hospedagem hospedagem = carregarHospedagem();
    final Acomodacao acomodacao = hospedagem.getAcomodacao();
    final Hospede hospede = hospedagem.getHospede();
    final Endereco endereco = hospede.getEndereco();

    mockMVC
        .perform(get(format(URL))
            .param("acomodacao", acomodacao.getId().toString())
            .param("hospede", hospede.getId().toString())
            .param("status", hospedagem.getStatus().name())
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.elementos", hasSize(1)))
        .andExpect(jsonPath("$.elementos[0].id").value(hospedagem.getId()))
        .andExpect(jsonPath("$.elementos[0].acomodacao.id").value(acomodacao.getId()))
        .andExpect(jsonPath("$.elementos[0].acomodacao.nome").value(acomodacao.getNome()))
        .andExpect(jsonPath("$.elementos[0].acomodacao.descricao").value(acomodacao.getDescricao()))
        .andExpect(jsonPath("$.elementos[0].acomodacao.capacidade")
            .value(acomodacao.getCapacidade()))
        .andExpect(jsonPath("$.elementos[0].acomodacao.preco").value(acomodacao.getPreco()))
        .andExpect(jsonPath("$.elementos[0].hospede.nome").value(hospede.getNome()))
        .andExpect(jsonPath("$.elementos[0].hospede.documento").value(hospede.getDocumento()))
        .andExpect(jsonPath("$.elementos[0].hospede.dataNascimento")
            .value(hospede.getDataNascimento().format(ISO_DATE)))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.cep").value(endereco.getCep()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.numero").value(endereco.getNumero()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.bairro").value(endereco.getBairro()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.cidade").value(endereco.getCidade()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.estado").value(endereco.getEstado()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.logradouro")
            .value(endereco.getLogradouro()))
        .andExpect(jsonPath("$.elementos[0].hospede.endereco.complemento")
            .value(endereco.getComplemento()))
        .andExpect(jsonPath("$.elementos[0].hospede.email").value(hospede.getEmail()))
        .andExpect(jsonPath("$.elementos[0].hospede.telefone").value(hospede.getTelefone()))
        .andExpect(jsonPath("$.elementos[0].hospede.celular").value(hospede.getCelular()))
        .andExpect(
            jsonPath("$.elementos[0].acompanhantes", hasSize(hospedagem.getAcompanhantes().size())))
        .andExpect(jsonPath("$.elementos[0].dataEntrada")
            .value(hospedagem.getDataEntrada().format(ISO_DATE)))
        .andExpect(jsonPath("$.elementos[0].dataSaida")
            .value(hospedagem.getDataSaida().format(ISO_DATE)))
        .andExpect(jsonPath("$.elementos[0].status").value(hospedagem.getStatus().name()))
        .andExpect(jsonPath("$.pagina").value(0))
        .andExpect(jsonPath("$.tamanho").value(20))
        .andExpect(jsonPath("$.totalPaginas").value(1))
        .andExpect(jsonPath("$.totalElementos").value(1));
  }

  private Hospedagem carregarHospedagem() {
    final Hospedagem hospedagem = from(Hospedagem.class).gimme(VALIDO_SEM_ID.name());
    hospedagem.setHospede(hospedeDatabaseGateway.save(hospedagem.getHospede()));
    hospedagem.setAcomodacao(acomodacaoDatabaseGateway.save(hospedagem.getAcomodacao()));
    hospedagem.setAcompanhantes(hospedagem.getAcompanhantes()
        .stream()
        .map(acompanhante -> {
          acompanhante.setHospede(hospedagem.getHospede());
          return acompanhanteDatabaseGateway.save(acompanhante);
        })
        .collect(toList()));

    return hospedagemDatabaseGateway.save(hospedagem);
  }
}
