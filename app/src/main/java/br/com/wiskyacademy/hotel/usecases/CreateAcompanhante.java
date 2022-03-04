package br.com.wiskyacademy.hotel.usecases;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CreateAcompanhante {

  public Acompanhante execute(final Integer hospedeId, final Acompanhante acompanhante) {
    return null;
  }
}
