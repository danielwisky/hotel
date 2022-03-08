package br.com.wiskyacademy.hotel.usecases;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.exceptions.BusinessValidationException;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CriarHospedeTest extends UnitTest {

  @InjectMocks
  private CriarHospede criarHospede;

  @Mock
  private HospedeDatabaseGateway hospedeDatabaseGateway;

  @Captor
  private ArgumentCaptor<Hospede> hospedeArgumentCaptor;

  @Test
  void deveExecutar() {
    final Hospede hospede = from(Hospede.class).gimme(VALIDO.name());

    when(hospedeDatabaseGateway.existsByEmail(hospede.getEmail())).thenReturn(false);

    criarHospede.executar(hospede);

    verify(hospedeDatabaseGateway).save(hospedeArgumentCaptor.capture());

    final Hospede hospedeCapturado = hospedeArgumentCaptor.getValue();
    assertNotNull(hospedeCapturado);
  }

  @Test
  void deveLancarExceptionBusinessValidationExceptionQuandoEmailJaCadastrado() {
    final Hospede hospede = from(Hospede.class).gimme(VALIDO.name());

    when(hospedeDatabaseGateway.existsByEmail(hospede.getEmail())).thenReturn(true);

    assertThrows(BusinessValidationException.class,
        () -> criarHospede.executar(hospede));
  }
}