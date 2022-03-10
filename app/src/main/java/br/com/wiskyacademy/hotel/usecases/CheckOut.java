package br.com.wiskyacademy.hotel.usecases;

import static br.com.wiskyacademy.hotel.utils.DateUtils.anteriorDataMinimaAtual;
import static br.com.wiskyacademy.hotel.utils.DateUtils.posteriorDataMaximaAtual;
import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.exceptions.BusinessValidationException;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckOut {

  private final HospedagemDatabaseGateway hospedagemDatabaseGateway;

  public Hospedagem executar(final Integer hospedagemId) {

    final Hospedagem hospedagem = hospedagemDatabaseGateway.findById(hospedagemId)
        .orElseThrow(ResourceNotFoundException::new);

    validar(hospedagem);

    hospedagem.setDataCheckOut(now());
    hospedagem.setDataAtualizacao(now());

    return hospedagemDatabaseGateway.save(hospedagem);
  }

  private void validar(final Hospedagem hospedagem) {
    if (isNull(hospedagem.getDataCheckIn())) {
      throw new BusinessValidationException("check-in necessário");
    }

    if (nonNull(hospedagem.getDataCheckOut())) {
      throw new BusinessValidationException("o check-out já foi realizado");
    }

    if (anteriorDataMinimaAtual(hospedagem.getDataEntrada())
        || posteriorDataMaximaAtual(hospedagem.getDataSaida())) {
      throw new BusinessValidationException("check-out fora do período de hospedagem");
    }
  }
}
