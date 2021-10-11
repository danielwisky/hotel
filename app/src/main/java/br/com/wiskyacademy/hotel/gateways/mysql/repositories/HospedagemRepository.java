package br.com.wiskyacademy.hotel.gateways.mysql.repositories;

import br.com.wiskyacademy.hotel.gateways.mysql.entities.HospedagemEntity;
import org.springframework.data.repository.CrudRepository;

public interface HospedagemRepository extends CrudRepository<HospedagemEntity, Integer> {

}