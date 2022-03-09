package br.com.wiskyacademy.hotel.usecases.validations;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.exceptions.BusinessValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidaCapacidadeAcomodacao {

  public void executa(final Acomodacao acomodacao, final Hospedagem hospedagem) {
    final Integer capacidade = acomodacao.getCapacidade();
    final long qtdAcompanhantes = emptyIfNull(hospedagem.getAcompanhantes()).stream().count();
    final long qtdHospedes = qtdAcompanhantes + INTEGER_ONE;

    if (capacidade < qtdHospedes) {
      throw new BusinessValidationException(
          String.format("A capacidade máxima nesta acomodação é de %s hóspede(s).", capacidade));
    }
  }
}
