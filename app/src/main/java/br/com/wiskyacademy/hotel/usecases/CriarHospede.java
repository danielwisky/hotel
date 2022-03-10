package br.com.wiskyacademy.hotel.usecases;

import static java.time.LocalDateTime.now;

import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.exceptions.BusinessValidationException;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CriarHospede {

  private final HospedeDatabaseGateway hospedeDatabaseGateway;

  public Hospede executar(final Hospede hospede) {
    validarHospede(hospede);
    hospede.setDataAtualizacao(now());
    return hospedeDatabaseGateway.save(hospede);
  }

  private void validarHospede(final Hospede hospede) {
    if (hospedeDatabaseGateway.existsByEmail(hospede.getEmail())) {
      throw new BusinessValidationException(
          String.format("email %s já cadastrado.", hospede.getEmail()));
    }
  }
}
