package br.com.wiskyacademy.hotel.usecases;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CalculaValorHospedagem {

  public Float executa(final Float preco, final LocalDate dataEntrada, final LocalDate dataSaida) {
    Assert.notNull(preco, "preço: campo é obrigatório");
    Assert.notNull(dataEntrada, "dataEntrada: campo é obrigatório");
    Assert.notNull(dataSaida, "dataSaida: campo é obrigatório");

    long qtdDias = DAYS.between(dataEntrada, dataSaida);
    return preco * qtdDias;
  }
}