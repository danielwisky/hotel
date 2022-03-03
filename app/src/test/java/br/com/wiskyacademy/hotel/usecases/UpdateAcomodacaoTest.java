package br.com.wiskyacademy.hotel.usecases;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_OUTRO_NOME_E_DESCRICAO;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.AcomodacaoDatabaseGateway;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UpdateAcomodacaoTest extends UnitTest {

  @InjectMocks
  private UpdateAcomodacao updateAcomodacao;

  @Mock
  private AcomodacaoDatabaseGateway acomodacaoDatabaseGateway;

  @Captor
  private ArgumentCaptor<Acomodacao> acomodacaoArgumentCaptor;

  @Test
  void deveExecutar() {

    final Acomodacao acomodacao = from(Acomodacao.class).gimme(VALIDO.name());
    final Acomodacao acomodacaoComOutroNome =
        from(Acomodacao.class).gimme(VALIDO_OUTRO_NOME_E_DESCRICAO.name());

    when(acomodacaoDatabaseGateway.findById(acomodacao.getId()))
        .thenReturn(of(acomodacao));

    updateAcomodacao.execute(acomodacao.getId(), acomodacaoComOutroNome);

    verify(acomodacaoDatabaseGateway).save(acomodacaoArgumentCaptor.capture());

    final Acomodacao acomodacaoCapturada = acomodacaoArgumentCaptor.getValue();
    assertEquals(acomodacaoComOutroNome.getNome(), acomodacaoCapturada.getNome());
    assertEquals(acomodacaoComOutroNome.getDescricao(), acomodacaoCapturada.getDescricao());
    assertEquals(acomodacaoComOutroNome.getCapacidade(), acomodacaoCapturada.getCapacidade());
    assertEquals(acomodacaoComOutroNome.getPreco(), acomodacaoCapturada.getPreco());
  }

  @Test
  void deveLancarExceptionResourceNotFoundQuandoNaoExistirAcomodacao() {
    final Acomodacao acomodacao = from(Acomodacao.class).gimme(VALIDO.name());

    when(acomodacaoDatabaseGateway.findById(acomodacao.getId())).thenReturn(empty());

    assertThrows(ResourceNotFoundException.class,
        () -> updateAcomodacao.execute(acomodacao.getId(), acomodacao));
  }
}