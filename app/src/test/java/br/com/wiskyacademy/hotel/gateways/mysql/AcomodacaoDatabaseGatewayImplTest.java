package br.com.wiskyacademy.hotel.gateways.mysql;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.gateways.mysql.entities.AcomodacaoEntity;
import br.com.wiskyacademy.hotel.gateways.mysql.repositories.AcomodacaoRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class AcomodacaoDatabaseGatewayImplTest extends UnitTest {

  @InjectMocks
  private AcomodacaoDatabaseGatewayImpl acomodacaoDatabaseGateway;

  @Mock
  private AcomodacaoRepository acomodacaoRepository;

  @Test
  void deveSalvar() {
    final Acomodacao acomodacao = Fixture.from(Acomodacao.class).gimme(VALIDO.name());
    final AcomodacaoEntity acomodacaoEntity = new AcomodacaoEntity(acomodacao);
    when(acomodacaoRepository.save(acomodacaoEntity)).thenReturn(acomodacaoEntity);

    final Acomodacao retorno = acomodacaoDatabaseGateway.save(acomodacao);

    verify(acomodacaoRepository).save(acomodacaoEntity);
    assertNotNull(retorno);
  }

  @Test
  void aoBuscarPorIdDeveRetornarAcomodacao() {

    final Acomodacao acomodacao = Fixture.from(Acomodacao.class).gimme(VALIDO.name());
    when(acomodacaoRepository.findById(acomodacao.getId()))
        .thenReturn(Optional.of(new AcomodacaoEntity(acomodacao)));

    final Optional<Acomodacao> retorno =
        acomodacaoDatabaseGateway.findById(acomodacao.getId());

    assertFalse(retorno.isEmpty());
  }

  @Test
  void aoBuscarPorIdDeveRetornarVazioQuandoNaoEncontrado() {

    final Acomodacao acomodacao = Fixture.from(Acomodacao.class).gimme(VALIDO.name());
    when(acomodacaoRepository.findById(acomodacao.getId()))
        .thenReturn(Optional.empty());

    final Optional<Acomodacao> retorno =
        acomodacaoDatabaseGateway.findById(acomodacao.getId());

    assertTrue(retorno.isEmpty());
  }
}