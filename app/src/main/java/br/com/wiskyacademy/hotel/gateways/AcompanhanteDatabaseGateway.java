package br.com.wiskyacademy.hotel.gateways;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import java.util.Optional;

public interface AcompanhanteDatabaseGateway {

  Acompanhante save(Acompanhante acompanhante);

  Optional<Acompanhante> findById(Integer id);
}