package br.com.wiskyacademy.hotel.gateways;

import br.com.wiskyacademy.hotel.domains.Hospede;
import java.util.Optional;

public interface HospedeDatabaseGateway {

  Hospede save(Hospede hospede);

  Optional<Hospede> findById(Integer id);
}