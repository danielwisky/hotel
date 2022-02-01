package br.com.wiskyacademy.hotel.gateways;

import br.com.wiskyacademy.hotel.domains.FiltroHospede;
import br.com.wiskyacademy.hotel.domains.Hospede;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface HospedeDatabaseGateway {

  Hospede save(Hospede hospede);

  Optional<Hospede> findById(Integer id);

  Page<Hospede> search(FiltroHospede filtro, Pageable pageable);
}