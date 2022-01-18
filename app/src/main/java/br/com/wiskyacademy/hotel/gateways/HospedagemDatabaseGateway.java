package br.com.wiskyacademy.hotel.gateways;

import br.com.wiskyacademy.hotel.domains.Hospedagem;
import java.util.Optional;

public interface HospedagemDatabaseGateway {

  Hospedagem save(Hospedagem hospedagem);

  Optional<Hospedagem> findById(Integer id);
}