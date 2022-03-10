package br.com.wiskyacademy.hotel.usecases;

import static br.com.wiskyacademy.hotel.utils.DateUtils.isBetweenCurrentPeriod;
import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;

import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.exceptions.BusinessValidationException;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckIn {

  private final HospedagemDatabaseGateway hospedagemDatabaseGateway;

  public Hospedagem executar(final Integer hospedagemId) {

    final Hospedagem hospedagem = hospedagemDatabaseGateway.findById(hospedagemId)
        .orElseThrow(ResourceNotFoundException::new);

    validar(hospedagem);

    hospedagem.setDataCheckIn(now());
    hospedagem.setDataAtualizacao(now());

    return hospedagemDatabaseGateway.save(hospedagem);
  }

  private void validar(Hospedagem hospedagem) {
    if (nonNull(hospedagem.getDataCheckIn())) {
      throw new BusinessValidationException("o check-in já foi realizado");
    }

    if (!isBetweenCurrentPeriod(hospedagem.getDataEntrada(), hospedagem.getDataSaida())) {
      throw new BusinessValidationException("check-in fora do período de hospedagem");
    }
  }
}
