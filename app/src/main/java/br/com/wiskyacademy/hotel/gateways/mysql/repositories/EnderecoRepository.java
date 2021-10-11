package br.com.wiskyacademy.hotel.gateways.mysql.repositories;

import br.com.wiskyacademy.hotel.gateways.mysql.entities.EnderecoEntity;
import org.springframework.data.repository.CrudRepository;

public interface EnderecoRepository extends CrudRepository<EnderecoEntity, Integer> {

}