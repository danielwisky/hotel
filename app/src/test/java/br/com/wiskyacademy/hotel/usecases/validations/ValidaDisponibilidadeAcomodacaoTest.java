package br.com.wiskyacademy.hotel.usecases.validations;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.exceptions.BusinessValidationException;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ValidaDisponibilidadeAcomodacaoTest extends UnitTest {

  @InjectMocks
  private ValidaDisponibilidadeAcomodacao validaDisponibilidadeAcomodacao;

  @Mock
  private HospedagemDatabaseGateway hospedagemDatabaseGateway;

  @Test
  public void deveValidarAcomodacaoDisponivel() {
    final Acomodacao acomodacao = from(Acomodacao.class).gimme(VALIDO.name());
    final Hospedagem hospedagem = from(Hospedagem.class).gimme(VALIDO.name());

    when(hospedagemDatabaseGateway
        .existsByAcomodacaoAndPeriodoDeEstadia(
            acomodacao.getId(), hospedagem.getDataEntrada(), hospedagem.getDataSaida()))
        .thenReturn(false);

    assertDoesNotThrow(() -> validaDisponibilidadeAcomodacao.executa(acomodacao, hospedagem));
  }

  @Test
  public void deveValidarAcomodacaoIndisponivel() {
    final Acomodacao acomodacao = from(Acomodacao.class).gimme(VALIDO.name());
    final Hospedagem hospedagem = from(Hospedagem.class).gimme(VALIDO.name());

    when(hospedagemDatabaseGateway
        .existsByAcomodacaoAndPeriodoDeEstadia(
            acomodacao.getId(), hospedagem.getDataEntrada(), hospedagem.getDataSaida()))
        .thenReturn(true);

    assertThrows(BusinessValidationException.class,
        () -> validaDisponibilidadeAcomodacao.executa(acomodacao, hospedagem));
  }
}