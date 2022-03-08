package br.com.wiskyacademy.hotel.usecases;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.exceptions.BusinessValidationException;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlterarHospede {

  private final HospedeDatabaseGateway hospedeDatabaseGateway;

  public Hospede executar(final Integer id, final Hospede hospede) {

    final Hospede hospedeDatabase = hospedeDatabaseGateway.findById(id)
        .orElseThrow(ResourceNotFoundException::new);

    validarHospede(hospede, hospedeDatabase);

    hospede.setId(hospedeDatabase.getId());
    hospede.getEndereco().setId(hospedeDatabase.getEndereco().getId());

    return hospedeDatabaseGateway.save(hospede);
  }

  private void validarHospede(final Hospede hospede, final Hospede hospedeDatabase) {
    if (!equalsIgnoreCase(hospede.getEmail(), hospedeDatabase.getEmail())
        && hospedeDatabaseGateway.existsByEmail(hospede.getEmail())) {
      throw new BusinessValidationException(
          String.format("email %s já cadastrado.", hospede.getEmail()));
    }
  }
}