package br.com.wiskyacademy.hotel.gateways;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.FiltroAcomodacao;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AcomodacaoDatabaseGateway {

  Acomodacao save(Acomodacao acomodacao);

  Optional<Acomodacao> findById(Integer id);

  Page<Acomodacao> search(FiltroAcomodacao filtro, Pageable pageable);
}