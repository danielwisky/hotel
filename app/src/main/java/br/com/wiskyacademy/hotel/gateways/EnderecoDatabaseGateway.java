package br.com.wiskyacademy.hotel.gateways;

import br.com.wiskyacademy.hotel.domains.Endereco;
import java.util.Optional;

public interface EnderecoDatabaseGateway {

  Endereco save(Endereco endereco);

  Optional<Endereco> findById(Integer id);
}