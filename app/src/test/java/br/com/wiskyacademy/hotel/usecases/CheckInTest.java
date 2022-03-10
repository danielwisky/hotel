package br.com.wiskyacademy.hotel.usecases;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static java.time.LocalDateTime.now;
import static java.util.Optional.empty;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_TWO;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.exceptions.BusinessValidationException;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CheckInTest extends UnitTest {

  @InjectMocks
  private CheckIn checkIn;

  @Mock
  private HospedagemDatabaseGateway hospedagemDatabaseGateway;

  @Captor
  private ArgumentCaptor<Hospedagem> hospedagemArgumentCaptor;

  @Test
  public void deveExecutar() {

    final Hospedagem hospedagem = from(Hospedagem.class).gimme(VALIDO.name());
    hospedagem.setDataEntrada(LocalDate.now().minusDays(INTEGER_ONE));
    hospedagem.setDataSaida(LocalDate.now().plusDays(INTEGER_ONE));

    when(hospedagemDatabaseGateway.findById(hospedagem.getId()))
        .thenReturn(Optional.of(hospedagem));

    checkIn.executar(hospedagem.getId());

    verify(hospedagemDatabaseGateway).save(hospedagemArgumentCaptor.capture());

    final Hospedagem hospedagemCapturada = hospedagemArgumentCaptor.getValue();
    assertNotNull(hospedagemCapturada.getDataCheckIn());
    assertNotNull(hospedagemCapturada.getDataAtualizacao());
  }

  @Test
  public void deveLancarExceptionResourceNotFoundQuandoNaoExistirHospedagem() {
    when(hospedagemDatabaseGateway.findById(INTEGER_ONE)).thenReturn(empty());
    assertThrows(ResourceNotFoundException.class, () -> checkIn.executar(INTEGER_ONE));
  }

  @Test
  public void deveLancarBusinessValidationExceptionQuandoCheckInJaFoiRealizado() {

    final Hospedagem hospedagem = from(Hospedagem.class).gimme(VALIDO.name());
    hospedagem.setDataCheckIn(now());

    when(hospedagemDatabaseGateway.findById(hospedagem.getId()))
        .thenReturn(Optional.of(hospedagem));

    assertThrows(BusinessValidationException.class,
        () -> checkIn.executar(hospedagem.getId()));
  }

  @Test
  public void deveLancarBusinessValidationExceptionQuandoCheckInRealizadoForaDoPeriodo() {

    final Hospedagem hospedagem = from(Hospedagem.class).gimme(VALIDO.name());
    hospedagem.setDataEntrada(LocalDate.now().plusDays(INTEGER_ONE));
    hospedagem.setDataSaida(LocalDate.now().plusDays(INTEGER_TWO));

    when(hospedagemDatabaseGateway.findById(hospedagem.getId()))
        .thenReturn(Optional.of(hospedagem));

    assertThrows(BusinessValidationException.class,
        () -> checkIn.executar(hospedagem.getId()));
  }
}