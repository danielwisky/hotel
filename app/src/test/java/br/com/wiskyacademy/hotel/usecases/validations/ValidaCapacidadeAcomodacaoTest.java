package br.com.wiskyacademy.hotel.usecases.validations;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_CAPACIDADE_ALTA;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_CAPACIDADE_BAIXA;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.exceptions.BusinessValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class ValidaCapacidadeAcomodacaoTest extends UnitTest {

  @InjectMocks
  private ValidaCapacidadeAcomodacao validaCapacidadeAcomodacao;

  @Test
  public void deveValidarCapacidadeMenorQueOLimite() {
    final Acomodacao acomodacao = from(Acomodacao.class).gimme(VALIDO_CAPACIDADE_ALTA.name());
    final Hospedagem hospedagem = from(Hospedagem.class).gimme(VALIDO.name());

    assertDoesNotThrow(() -> validaCapacidadeAcomodacao.executa(acomodacao, hospedagem));
  }

  @Test
  public void deveValidarCapacidadeMaiorQueOLimite() {
    final Acomodacao acomodacao = from(Acomodacao.class).gimme(VALIDO_CAPACIDADE_BAIXA.name());
    final Hospedagem hospedagem = from(Hospedagem.class).gimme(VALIDO.name());

    assertThrows(BusinessValidationException.class,
        () -> validaCapacidadeAcomodacao.executa(acomodacao, hospedagem));
  }
}
