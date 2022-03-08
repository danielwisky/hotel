package br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories;

import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface HospedagemRepository extends
    CrudRepository<HospedagemEntity, Integer>,
    JpaSpecificationExecutor<HospedagemEntity> {

}