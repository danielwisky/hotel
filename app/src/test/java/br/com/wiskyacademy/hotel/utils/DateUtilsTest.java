package br.com.wiskyacademy.hotel.utils;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

import br.com.wiskyacademy.hotel.UnitTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DateUtilsTest extends UnitTest {

  @Test
  public void deveValidarDataAnteriorMenorQueDataAtual() {
    final LocalDate ontem = LocalDate.now().minusDays(INTEGER_ONE);
    final boolean afterOfMinDate = DateUtils.isAfterOfCurrentDate(ontem);
    Assertions.assertTrue(afterOfMinDate);
  }

  @Test
  public void deveValidarDataAnteriorMaiorQueDataAtual() {
    final LocalDate amanha = LocalDate.now().plusDays(INTEGER_ONE);
    final boolean afterOfMinDate = DateUtils.isAfterOfCurrentDate(amanha);
    Assertions.assertFalse(afterOfMinDate);
  }

  @Test
  public void deveValidarDataPosteriorMenorQueDataAtual() {
    final LocalDate ontem = LocalDate.now().minusDays(INTEGER_ONE);
    final boolean isBeforeOfMaxDate = DateUtils.isBeforeOfCurrentDate(ontem);
    Assertions.assertFalse(isBeforeOfMaxDate);
  }

  @Test
  public void deveValidarDataPosteriorMaiorQueDataAtual() {
    final LocalDate amanha = LocalDate.now().plusDays(INTEGER_ONE);
    final boolean isBeforeOfMaxDate = DateUtils.isBeforeOfCurrentDate(amanha);
    Assertions.assertTrue(isBeforeOfMaxDate);
  }
}
