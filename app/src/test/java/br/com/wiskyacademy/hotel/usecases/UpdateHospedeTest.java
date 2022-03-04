package br.com.wiskyacademy.hotel.usecases;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_OUTRO_NOME_E_DESCRICAO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_OUTRO_NOME_E_DOCUMENTO;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UpdateHospedeTest extends UnitTest {

  @InjectMocks
  private UpdateHospede updateHospede;

  @Mock
  private HospedeDatabaseGateway hospedeDatabaseGateway;

  @Captor
  private ArgumentCaptor<Hospede> hospedeArgumentCaptor;

  @Test
  void deveExecutar() {

    final Hospede hospede = from(Hospede.class).gimme(VALIDO.name());
    final Hospede hospedeComOutroNome =
        from(Hospede.class).gimme(VALIDO_OUTRO_NOME_E_DOCUMENTO.name());

    when(hospedeDatabaseGateway.findById(hospede.getId()))
        .thenReturn(of(hospede));

    updateHospede.execute(hospede.getId(), hospedeComOutroNome);

    verify(hospedeDatabaseGateway).save(hospedeArgumentCaptor.capture());

    final Hospede hospedeCapturado = hospedeArgumentCaptor.getValue();
    assertEquals(hospedeComOutroNome.getNome(), hospedeCapturado.getNome());
    assertEquals(hospedeComOutroNome.getDocumento(), hospedeCapturado.getDocumento());
    assertEquals(hospedeComOutroNome.getEmail(), hospedeCapturado.getEmail());
    assertEquals(hospedeComOutroNome.getTelefone(), hospedeCapturado.getTelefone());
  }

  @Test
  void deveLancarExceptionResourceNotFoundQuandoNaoExistirHospede() {
    final Hospede hospede = from(Hospede.class).gimme(VALIDO.name());

    when(hospedeDatabaseGateway.findById(hospede.getId())).thenReturn(empty());

    assertThrows(ResourceNotFoundException.class,
        () -> updateHospede.execute(hospede.getId(), hospede));
  }
}