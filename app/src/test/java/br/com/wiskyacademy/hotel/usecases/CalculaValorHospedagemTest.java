package br.com.wiskyacademy.hotel.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.wiskyacademy.hotel.UnitTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class CalculaValorHospedagemTest extends UnitTest {

  @InjectMocks
  private CalculaValorHospedagem calculaValorHospedagem;

  @Test
  public void deveExecutar() {

    final float valor = 100f;
    final LocalDate dataEntrada = LocalDate.of(2022, 3, 10);
    final LocalDate dataSaida = LocalDate.of(2022, 3, 20);

    final Float total = calculaValorHospedagem.executa(valor, dataEntrada, dataSaida);

    assertEquals(1000f, total);
  }
}
