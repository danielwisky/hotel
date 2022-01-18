package br.com.wiskyacademy.hotel.gateways;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import java.util.Optional;

public interface AcomodacaoDatabaseGateway {

  Acomodacao save(Acomodacao acomodacao);

  Optional<Acomodacao> findById(Integer id);
}