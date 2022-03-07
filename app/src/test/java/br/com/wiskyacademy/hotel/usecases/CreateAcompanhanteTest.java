package br.com.wiskyacademy.hotel.usecases;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.AcompanhanteDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CreateAcompanhanteTest extends UnitTest {

  @InjectMocks
  private CreateAcompanhante createAcompanhante;

  @Mock
  private HospedeDatabaseGateway hospedeDatabaseGateway;

  @Mock
  private AcompanhanteDatabaseGateway acompanhanteDatabaseGateway;

  @Captor
  private ArgumentCaptor<Acompanhante> acompanhanteArgumentCaptor;

  @Test
  void deveExecutar() {

    final Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO.name());
    final Hospede hospede = acompanhante.getHospede();

    when(hospedeDatabaseGateway.findById(hospede.getId())).thenReturn(of(hospede));

    createAcompanhante.execute(hospede.getId(), acompanhante);

    verify(acompanhanteDatabaseGateway).save(acompanhanteArgumentCaptor.capture());

    final Acompanhante acompanhanteCapturado = acompanhanteArgumentCaptor.getValue();
    assertNotNull(acompanhanteCapturado);
    assertEquals(acompanhante.getHospede(), acompanhanteCapturado.getHospede());
  }

  @Test
  void deveLancarExceptionResourceNotFoundQuandoNaoExistirHospede() {
    final Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO.name());
    final Hospede hospede = acompanhante.getHospede();

    when(hospedeDatabaseGateway.findById(hospede.getId())).thenReturn(empty());

    assertThrows(ResourceNotFoundException.class,
        () -> createAcompanhante.execute(hospede.getId(), acompanhante));
  }
}