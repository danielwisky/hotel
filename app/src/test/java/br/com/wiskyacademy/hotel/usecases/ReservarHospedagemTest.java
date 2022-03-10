package br.com.wiskyacademy.hotel.usecases;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.StatusHospedagem;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import br.com.wiskyacademy.hotel.usecases.validations.ValidaReservaAcomodacao;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

public class ReservarHospedagemTest extends UnitTest {

  @InjectMocks
  private ReservarHospedagem reservarHospedagem;

  @Spy
  private CalculaValorHospedagem calculaValorHospedagem;

  @Mock
  private ValidaReservaAcomodacao validaReservaAcomodacao;

  @Mock
  private HospedagemDatabaseGateway hospedagemDatabaseGateway;

  @Captor
  private ArgumentCaptor<Hospedagem> hospedagemArgumentCaptor;

  @Test
  public void deveExecutar() {

    final Hospedagem hospedagem = from(Hospedagem.class).gimme(VALIDO.name());
    final Acomodacao acomodacao = hospedagem.getAcomodacao();

    reservarHospedagem.executar(hospedagem);

    verify(validaReservaAcomodacao).executa(acomodacao, hospedagem);
    verify(calculaValorHospedagem).executa(
        acomodacao.getPreco(), hospedagem.getDataEntrada(), hospedagem.getDataSaida());
    verify(hospedagemDatabaseGateway).save(hospedagemArgumentCaptor.capture());

    final Hospedagem hospedagemCapturada = hospedagemArgumentCaptor.getValue();

    assertEquals(StatusHospedagem.RESERVADO, hospedagemCapturada.getStatus());
    assertNotNull(hospedagemCapturada.getValor());
    assertNotNull(hospedagemCapturada.getDataAtualizacao());
  }
}