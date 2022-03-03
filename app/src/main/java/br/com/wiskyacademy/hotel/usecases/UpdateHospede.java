package br.com.wiskyacademy.hotel.usecases;

import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateHospede {

  private final HospedeDatabaseGateway hospedeDatabaseGateway;

  public Hospede execute(final Integer id, final Hospede hospede) {

    final Hospede hospedeDatabase = hospedeDatabaseGateway.findById(id)
        .orElseThrow(ResourceNotFoundException::new);

    hospede.setId(hospedeDatabase.getId());
    hospede.getEndereco().setId(hospedeDatabase.getEndereco().getId());
    
    return hospedeDatabaseGateway.save(hospede);
  }
}