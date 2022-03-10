package br.com.wiskyacademy.hotel.usecases.validations;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static org.mockito.Mockito.verify;

import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ValidaReservaAcomodacaoTest extends UnitTest {

  @InjectMocks
  private ValidaReservaAcomodacao validaReservaAcomodacao;

  @Mock
  private ValidaCapacidadeAcomodacao validaCapacidadeAcomodacao;

  @Mock
  private ValidaDisponibilidadeAcomodacao validaDisponibilidadeAcomodacao;

  @Test
  public void deveExecutar() {
    final Acomodacao acomodacao = from(Acomodacao.class).gimme(VALIDO.name());
    final Hospedagem hospedagem = from(Hospedagem.class).gimme(VALIDO.name());

    validaReservaAcomodacao.executa(acomodacao, hospedagem);

    verify(validaCapacidadeAcomodacao).executa(acomodacao, hospedagem);
    verify(validaDisponibilidadeAcomodacao).executa(acomodacao, hospedagem);
  }
}
