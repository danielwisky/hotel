package br.com.wiskyacademy.hotel.gateways;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.FiltroAcompanhante;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AcompanhanteDatabaseGateway {

  Acompanhante save(Acompanhante acompanhante);

  Optional<Acompanhante> findByHospedeIdAndAcompanhanteId(Integer hospedeId, Integer id);

  Page<Acompanhante> search(FiltroAcompanhante filtro, Pageable pageable);
}