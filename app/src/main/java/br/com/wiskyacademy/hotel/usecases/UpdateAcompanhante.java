package br.com.wiskyacademy.hotel.usecases;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UpdateAcompanhante {

  public Acompanhante execute(
      final Integer hospedeId,
      final Integer acompanhanteId,
      final Acompanhante acompanhante) {
    return null;
  }
}
