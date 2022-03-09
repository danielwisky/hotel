package br.com.wiskyacademy.hotel.usecases.validations;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidaReservaAcomodacao {

  private final ValidaCapacidadeAcomodacao validaCapacidadeAcomodacao;
  private final ValidaDisponibilidadeAcomodacao validaDisponibilidadeAcomodacao;

  public void executa(final Acomodacao acomodacao, final Hospedagem hospedagem) {
    validaCapacidadeAcomodacao.executa(acomodacao, hospedagem);
    validaDisponibilidadeAcomodacao.executa(acomodacao, hospedagem);
  }
}
