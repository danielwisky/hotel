package br.com.wiskyacademy.hotel.gateways;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.FiltroAcompanhante;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AcompanhanteDatabaseGateway {

  Acompanhante save(Acompanhante acompanhante);

  Optional<Acompanhante> findByHospedeIdAndAcompanhanteId(
      Integer hospedeId, Integer acompanhanteId);

  List<Acompanhante> findByHospedeIdAndAcompanhantesId(
      Integer hospedeId, List<Integer> acompanhantesId);

  Page<Acompanhante> search(FiltroAcompanhante filtro, Pageable pageable);
}