package br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories;

import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity;
import org.springframework.data.repository.CrudRepository;

public interface AcomodacaoRepository extends CrudRepository<AcomodacaoEntity, Integer> {

}