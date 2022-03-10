package br.com.wiskyacademy.hotel.usecases;

import static java.time.LocalDateTime.now;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.StatusHospedagem;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import br.com.wiskyacademy.hotel.usecases.validations.ValidaReservaAcomodacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservarHospedagem {

  private final CalculaValorHospedagem calculaValorHospedagem;
  private final ValidaReservaAcomodacao validaReservaAcomodacao;

  private final HospedagemDatabaseGateway hospedagemDatabaseGateway;

  public Hospedagem executar(final Hospedagem hospedagem) {

    final Acomodacao acomodacao = hospedagem.getAcomodacao();
    validaReservaAcomodacao.executa(acomodacao, hospedagem);

    hospedagem.setValor(calculaValorHospedagem.executa(
        acomodacao.getPreco(), hospedagem.getDataEntrada(), hospedagem.getDataSaida()));
    hospedagem.setStatus(StatusHospedagem.RESERVADO);
    hospedagem.setDataAtualizacao(now());

    return hospedagemDatabaseGateway.save(hospedagem);
  }
}