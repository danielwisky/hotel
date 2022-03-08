package br.com.wiskyacademy.hotel.usecases;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_OUTRO_NOME_E_DOCUMENTO;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.AcompanhanteDatabaseGateway;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class AlterarAcompanhanteTest extends UnitTest {

  @InjectMocks
  private AlterarAcompanhante alterarAcompanhante;

  @Mock
  private AcompanhanteDatabaseGateway acompanhanteDatabaseGateway;

  @Captor
  private ArgumentCaptor<Acompanhante> acompanhanteArgumentCaptor;

  @Test
  void deveExecutar() {

    final Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO.name());
    final Acompanhante acompanhanteComOutroNome =
        from(Acompanhante.class).gimme(VALIDO_OUTRO_NOME_E_DOCUMENTO.name());
    final Hospede hospede = acompanhante.getHospede();

    when(acompanhanteDatabaseGateway
        .findByHospedeIdAndAcompanhanteId(hospede.getId(), acompanhante.getId()))
        .thenReturn(of(acompanhante));

    alterarAcompanhante.executar(hospede.getId(), acompanhante.getId(), acompanhanteComOutroNome);

    verify(acompanhanteDatabaseGateway).save(acompanhanteArgumentCaptor.capture());

    final Acompanhante acompanhanteCapturado = acompanhanteArgumentCaptor.getValue();
    assertEquals(acompanhanteComOutroNome.getNome(), acompanhanteCapturado.getNome());
    assertEquals(acompanhanteComOutroNome.getDocumento(), acompanhanteCapturado.getDocumento());
  }

  @Test
  void deveLancarExceptionResourceNotFoundQuandoNaoExistirAcompanhante() {
    final Acompanhante acompanhante = from(Acompanhante.class).gimme(VALIDO.name());
    final Hospede hospede = acompanhante.getHospede();

    when(acompanhanteDatabaseGateway
        .findByHospedeIdAndAcompanhanteId(hospede.getId(), acompanhante.getId()))
        .thenReturn(empty());

    assertThrows(ResourceNotFoundException.class,
        () -> alterarAcompanhante.executar(hospede.getId(), acompanhante.getId(), acompanhante));
  }
}