package br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories;

import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface HospedeRepository extends
    CrudRepository<HospedeEntity, Integer>,
    JpaSpecificationExecutor<HospedeEntity> {

  boolean existsByEmail(String email);

  boolean existsByEmailAndIdNot(String email, Integer id);
}
