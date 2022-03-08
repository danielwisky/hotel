package br.com.wiskyacademy.hotel.usecases;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.AcompanhanteDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CriarAcompanhante {

  private final HospedeDatabaseGateway hospedeDatabaseGateway;
  private final AcompanhanteDatabaseGateway acompanhanteDatabaseGateway;

  public Acompanhante executar(final Integer hospedeId, final Acompanhante acompanhante) {

    final Hospede hospedeDatabase = hospedeDatabaseGateway.findById(hospedeId)
        .orElseThrow(ResourceNotFoundException::new);

    acompanhante.setHospede(hospedeDatabase);

    return acompanhanteDatabaseGateway.save(acompanhante);
  }
}
