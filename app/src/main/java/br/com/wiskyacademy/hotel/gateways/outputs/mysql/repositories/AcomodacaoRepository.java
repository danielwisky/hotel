package br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories;

import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AcomodacaoRepository extends
    CrudRepository<AcomodacaoEntity, Integer>,
    JpaSpecificationExecutor<AcomodacaoEntity> {

}