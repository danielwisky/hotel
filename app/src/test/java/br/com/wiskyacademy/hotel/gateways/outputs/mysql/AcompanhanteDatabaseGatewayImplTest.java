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
import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcompanhanteEntity;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.AcompanhanteRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class AcompanhanteDatabaseGatewayImplTest extends UnitTest {

  @InjectMocks
  private AcompanhanteDatabaseGatewayImpl acompanhanteDatabaseGateway;

  @Mock
  private AcompanhanteRepository acompanhanteRepository;

  @Test
  void deveSalvar() {
    final Acompanhante acompanhante = Fixture.from(Acompanhante.class).gimme(VALIDO.name());
    final AcompanhanteEntity acompanhanteEntity = new AcompanhanteEntity(acompanhante);
    when(acompanhanteRepository.save(any(AcompanhanteEntity.class))).thenReturn(acompanhanteEntity);

    final Acompanhante retorno = acompanhanteDatabaseGateway.save(acompanhante);

    verify(acompanhanteRepository).save(any(AcompanhanteEntity.class));
    assertNotNull(retorno);
  }

  @Test
  void aoBuscarPorIdDeveRetornarAcompanhante() {

    final Acompanhante acompanhante = Fixture.from(Acompanhante.class).gimme(VALIDO.name());
    when(acompanhanteRepository.findById(acompanhante.getId()))
        .thenReturn(Optional.of(new AcompanhanteEntity(acompanhante)));

    final Optional<Acompanhante> retorno =
        acompanhanteDatabaseGateway.findById(acompanhante.getId());

    assertFalse(retorno.isEmpty());
  }

  @Test
  void aoBuscarPorIdDeveRetornarVazioQuandoNaoEncontrado() {

    final Acompanhante acompanhante = Fixture.from(Acompanhante.class).gimme(VALIDO.name());
    when(acompanhanteRepository.findById(acompanhante.getId()))
        .thenReturn(Optional.empty());

    final Optional<Acompanhante> retorno =
        acompanhanteDatabaseGateway.findById(acompanhante.getId());

    assertTrue(retorno.isEmpty());
  }
}