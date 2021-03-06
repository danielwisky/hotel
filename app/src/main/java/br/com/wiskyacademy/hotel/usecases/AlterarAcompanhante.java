package br.com.wiskyacademy.hotel.usecases;

import static java.time.LocalDateTime.now;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.AcompanhanteDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AlterarAcompanhante {

  private final AcompanhanteDatabaseGateway acompanhanteDatabaseGateway;

  public Acompanhante executar(
      final Integer hospedeId,
      final Integer acompanhanteId,
      final Acompanhante acompanhante) {

    final Acompanhante acompanhanteDatabase = acompanhanteDatabaseGateway
        .findByHospedeIdAndAcompanhanteId(hospedeId, acompanhanteId)
        .orElseThrow(ResourceNotFoundException::new);

    acompanhante.setId(acompanhanteDatabase.getId());
    acompanhante.setHospede(acompanhanteDatabase.getHospede());
    acompanhante.setDataAtualizacao(now());

    return acompanhanteDatabaseGateway.save(acompanhante);
  }
}
