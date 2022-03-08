package br.com.wiskyacademy.hotel.gateways;

import br.com.wiskyacademy.hotel.domains.FiltroHospedagem;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HospedagemDatabaseGateway {

  Hospedagem save(Hospedagem hospedagem);

  Optional<Hospedagem> findById(Integer id);

  Page<Hospedagem> search(FiltroHospedagem filtro, Pageable pageable);
}