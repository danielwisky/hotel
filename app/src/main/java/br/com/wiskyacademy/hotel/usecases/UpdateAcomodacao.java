package br.com.wiskyacademy.hotel.usecases;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.AcomodacaoDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateAcomodacao {

  private final AcomodacaoDatabaseGateway acomodacaoDatabaseGateway;

  public Acomodacao execute(final Integer id, final Acomodacao acomodacao) {

    final Acomodacao acomodacaoDatabase = acomodacaoDatabaseGateway.findById(id)
        .orElseThrow(ResourceNotFoundException::new);

    acomodacaoDatabase.setNome(acomodacao.getNome());
    acomodacaoDatabase.setDescricao(acomodacao.getDescricao());
    acomodacaoDatabase.setCapacidade(acomodacao.getCapacidade());
    acomodacaoDatabase.setPreco(acomodacao.getPreco());

    return acomodacaoDatabaseGateway.save(acomodacaoDatabase);
  }
}