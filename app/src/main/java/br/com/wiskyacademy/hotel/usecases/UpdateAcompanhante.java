package br.com.wiskyacademy.hotel.usecases;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.AcompanhanteDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UpdateAcompanhante {

  private final HospedeDatabaseGateway hospedeDatabaseGateway;
  private final AcompanhanteDatabaseGateway acompanhanteDatabaseGateway;

  public Acompanhante execute(
      final Integer hospedeId,
      final Integer acompanhanteId,
      final Acompanhante acompanhante) {

    final Acompanhante acompanhanteDatabase = acompanhanteDatabaseGateway
        .findByHospedeIdAndAcompanhanteId(hospedeId, acompanhanteId)
        .orElseThrow(ResourceNotFoundException::new);

    acompanhante.setId(acompanhanteDatabase.getId());
    acompanhante.setHospede(acompanhanteDatabase.getHospede());

    return acompanhanteDatabaseGateway.save(acompanhante);
  }
}
