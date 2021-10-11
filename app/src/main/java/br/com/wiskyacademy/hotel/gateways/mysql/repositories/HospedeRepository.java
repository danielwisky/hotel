package br.com.wiskyacademy.hotel.gateways.mysql.repositories;

import br.com.wiskyacademy.hotel.gateways.mysql.entities.HospedeEntity;
import org.springframework.data.repository.CrudRepository;

public interface HospedeRepository extends CrudRepository<HospedeEntity, Integer> {

}