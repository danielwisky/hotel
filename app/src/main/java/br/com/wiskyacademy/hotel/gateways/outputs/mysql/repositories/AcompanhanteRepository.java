package br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories;

import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcompanhanteEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface AcompanhanteRepository extends
    CrudRepository<AcompanhanteEntity, Integer>,
    JpaSpecificationExecutor<AcompanhanteEntity> {

  Optional<AcompanhanteEntity> findByHospedeIdAndId(Integer hospedeId, Integer acompanhanteId);

  List<AcompanhanteEntity> findByHospedeIdAndIdIn(Integer hospedeId, List<Integer> acompanhantesId);
}