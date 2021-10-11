package br.com.wiskyacademy.hotel.gateways.mysql;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.gateways.mysql.entities.HospedeEntity;
import br.com.wiskyacademy.hotel.gateways.mysql.repositories.HospedeRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class HospedeDatabaseGatewayImplTest extends UnitTest {

  @InjectMocks
  private HospedeDatabaseGatewayImpl hospedeDatabaseGateway;

  @Mock
  private HospedeRepository hospedeRepository;

  @Test
  void deveSalvar() {
    final Hospede hospede = Fixture.from(Hospede.class).gimme(VALIDO.name());
    final HospedeEntity hospedeEntity = new HospedeEntity(hospede);
    when(hospedeRepository.save(hospedeEntity)).thenReturn(hospedeEntity);

    final Hospede retorno = hospedeDatabaseGateway.save(hospede);

    verify(hospedeRepository).save(hospedeEntity);
    assertNotNull(retorno);
  }

  @Test
  void aoBuscarPorIdDeveRetornarHospede() {

    final Hospede hospede = Fixture.from(Hospede.class).gimme(VALIDO.name());
    when(hospedeRepository.findById(hospede.getId()))
        .thenReturn(Optional.of(new HospedeEntity(hospede)));

    final Optional<Hospede> retorno =
        hospedeDatabaseGateway.findById(hospede.getId());

    assertFalse(retorno.isEmpty());
  }

  @Test
  void aoBuscarPorIdDeveRetornarVazioQuandoNaoEncontrado() {

    final Hospede hospede = Fixture.from(Hospede.class).gimme(VALIDO.name());
    when(hospedeRepository.findById(hospede.getId()))
        .thenReturn(Optional.empty());

    final Optional<Hospede> retorno =
        hospedeDatabaseGateway.findById(hospede.getId());

    assertTrue(retorno.isEmpty());
  }
}