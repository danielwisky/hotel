package br.com.wiskyacademy.hotel.usecases.validations;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.exceptions.BusinessValidationException;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidaDisponibilidadeAcomodacao {

  private final HospedagemDatabaseGateway hospedagemDatabaseGateway;

  public void executa(final Acomodacao acomodacao, final Hospedagem hospedagem) {
    if (existeReserva(acomodacao, hospedagem)) {
      throw new BusinessValidationException("Acomodação indisponível para o período selecionado.");
    }
  }

  private boolean existeReserva(final Acomodacao acomodacao, final Hospedagem hospedagem) {
    return hospedagemDatabaseGateway.existsByAcomodacaoAndPeriodoDeEstadia(
        acomodacao.getId(), hospedagem.getDataEntrada(), hospedagem.getDataSaida());
  }
}