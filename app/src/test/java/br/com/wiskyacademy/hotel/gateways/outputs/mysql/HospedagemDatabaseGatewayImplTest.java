package br.com.wiskyacademy.hotel.gateways.outputs.mysql;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.HospedagemRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class HospedagemDatabaseGatewayImplTest extends UnitTest {

  @InjectMocks
  private HospedagemDatabaseGatewayImpl hospedagemDatabaseGateway;

  @Mock
  private HospedagemRepository hospedagemRepository;

  @Test
  void deveSalvar() {
    final Hospedagem hospedagem = Fixture.from(Hospedagem.class).gimme(VALIDO.name());
    final HospedagemEntity hospedagemEntity = new HospedagemEntity(hospedagem);
    when(hospedagemRepository.save(any(HospedagemEntity.class))).thenReturn(hospedagemEntity);

    final Hospedagem retorno = hospedagemDatabaseGateway.save(hospedagem);

    verify(hospedagemRepository).save(any(HospedagemEntity.class));
    assertNotNull(retorno);
  }

  @Test
  void aoBuscarPorIdDeveRetornarHospedagem() {

    final Hospedagem hospedagem = Fixture.from(Hospedagem.class).gimme(VALIDO.name());
    when(hospedagemRepository.findById(hospedagem.getId()))
        .thenReturn(Optional.of(new HospedagemEntity(hospedagem)));

    final Optional<Hospedagem> retorno =
        hospedagemDatabaseGateway.findById(hospedagem.getId());

    assertFalse(retorno.isEmpty());
  }

  @Test
  void aoBuscarPorIdDeveRetornarVazioQuandoNaoEncontrado() {

    final Hospedagem hospedagem = Fixture.from(Hospedagem.class).gimme(VALIDO.name());
    when(hospedagemRepository.findById(hospedagem.getId()))
        .thenReturn(Optional.empty());

    final Optional<Hospedagem> retorno =
        hospedagemDatabaseGateway.findById(hospedagem.getId());

    assertTrue(retorno.isEmpty());
  }
}